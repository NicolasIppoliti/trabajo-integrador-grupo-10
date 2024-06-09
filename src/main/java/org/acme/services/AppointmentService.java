package org.acme.services;

import org.acme.models.dto.AppointmentDTO;
import org.acme.models.entities.AppointmentEntity;
import org.acme.models.entities.PatientEntity;
import org.acme.models.entities.DoctorEntity;
import org.acme.repositories.AppointmentRepository;
import org.acme.repositories.PatientRepository;
import org.acme.repositories.DoctorRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AppointmentService {

    @Inject
    AppointmentRepository appointmentRepository;

    @Inject
    PatientRepository patientRepository;

    @Inject
    DoctorRepository doctorRepository;

    public List<AppointmentEntity> listAll() {
        return appointmentRepository.listAll();
    }

    public AppointmentEntity findById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Transactional
    public void add(AppointmentEntity appointmentEntity, Long patientId, Long doctorId) {
        PatientEntity patient = patientRepository.findById(patientId);
        DoctorEntity doctor = doctorRepository.findById(doctorId);
        
        if (patient != null && doctor != null) {
            appointmentEntity.setPatient(patient);
            appointmentEntity.setDoctor(doctor);
            appointmentRepository.persist(appointmentEntity);
        } else {
            throw new IllegalArgumentException("Paciente o doctor no encontrado");
        }
    }

    @Transactional
    public void update(Long id, AppointmentDTO appointmentDTO) {
        AppointmentEntity existingAppointment = appointmentRepository.findById(id);
        if (existingAppointment == null) {
            throw new IllegalArgumentException("Turno no encontrado con id: " + id);
        }

        // Actualizo los campos existentes del turno
        existingAppointment.setDateHour(appointmentDTO.getDateHour());
        existingAppointment.setQueryReason(appointmentDTO.getQueryReason());

        // Traigo y seteo las entidades relacionadas
        if (appointmentDTO.getDoctorId() != null) {
            DoctorEntity doctor = doctorRepository.findById(appointmentDTO.getDoctorId());
            if (doctor == null) {
                throw new IllegalArgumentException("Doctor no encontrado con id: " + appointmentDTO.getDoctorId());
            }
            existingAppointment.setDoctor(doctor);
        }

        if (appointmentDTO.getPatientId() != null) {
            PatientEntity patient = patientRepository.findById(appointmentDTO.getPatientId());
            if (patient == null) {
                throw new IllegalArgumentException("Paciente no encontrado con id: " + appointmentDTO.getPatientId());
            }
            existingAppointment.setPatient(patient);
        }

        // Guardo el turno actualizado
        appointmentRepository.persist(existingAppointment);
    }


    @Transactional
    public void delete(Long id) {
        AppointmentEntity existingAppointment = appointmentRepository.findById(id);
        if (existingAppointment != null) {
            System.out.println("Deleting appointment with ID: " + id);
            if (existingAppointment.getDoctor() != null) {
                existingAppointment.getDoctor().getAppointments().remove(existingAppointment);
            }
            if (existingAppointment.getPatient() != null) {
                existingAppointment.getPatient().getAppointments().remove(existingAppointment);
            }
            appointmentRepository.delete(existingAppointment);
            System.out.println("Deleted appointment with ID: " + id);
        } else {
            System.out.println("Appointment with ID " + id + " not found.");
            throw new IllegalArgumentException("Turno no encontrado con id: " + id);
        }
    }
}
