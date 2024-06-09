package org.acme.services;

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
    public void update(AppointmentEntity appointmentEntity) {
        appointmentRepository.getEntityManager().merge(appointmentEntity);
    }

    @Transactional
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}
