package org.acme.services;

import org.acme.domain.Appointment;
import org.acme.models.entities.ScheduleEntity;
import org.acme.mappers.AppointmentMapper;
import org.acme.models.entities.AppointmentEntity;
import org.acme.models.entities.DoctorEntity;
import org.acme.models.entities.PatientEntity;
import org.acme.repositories.AppointmentRepository;
import org.acme.repositories.DoctorRepository;
import org.acme.repositories.PatientRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class AppointmentService {

    @Inject
    AppointmentRepository repository;

    @Inject
    DoctorRepository dRepository;

    @Inject
    PatientRepository pRepository;

    @Inject
    AppointmentMapper mapper;

    @Inject
    EntityManager entityManager;

    public List<Appointment> getAll() {
        return repository.listAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    public Appointment getById(Long id) {
        return mapper.toDomain(repository.findById(id));
    }

    @Transactional
    public AppointmentEntity create(Appointment appointment){
    DoctorEntity doctor = dRepository.findById(appointment.getDoctor_id());
    PatientEntity patient = pRepository.findById(appointment.getPatient_id());

    LocalDateTime dateTime = appointment.getDateHour();
    LocalDate date = dateTime.toLocalDate();
    LocalTime time = dateTime.toLocalTime();

    System.out.println("Ya reparti la fechaHora en fecha y hora");
    AppointmentEntity entity = new AppointmentEntity();
    entity.setDateHour(LocalDateTime.of(date, time));
    entity.setPatient(patient);
    entity.setDoctor(doctor);
    entity.setQueryReason(appointment.getQueryReason());
    System.out.println("setie los atributos. Estoy por entrar en la logica de validacion...");
    System.out.println(date);
    System.out.println(time);

    if (isAValidDate(date)){
        if (doctorWorksThatDay(appointment.getDoctor_id(), date) && doctorWorksThatDayAndTime(appointment.getDoctor_id(), date, time)) {
            System.out.println("Parece que el doctor trabaja ese dia a esa hora");
            if (!scheduleHaveAppointments(appointment.getDoctor_id(), dateTime)) {
                System.out.println("Parece que paso la validacion.Estoy a un paso de persistirlo...");
                repository.persist(entity);
                return entity;
            } else {
                throw new IllegalArgumentException("The doctor already has an appointment at the specified time or within the 30 minutes before.");
            }
        }
    }

    throw new IllegalArgumentException("The date and time are not valid.");
}

    @Transactional
    public AppointmentEntity update(Long id, Appointment appointment) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            LocalDate date = appointment.getDateHour().toLocalDate();
            LocalTime time = appointment.getDateHour().toLocalTime();


            if (isAValidDate(date)){
                if (doctorWorksThatDay(appointment.getDoctor_id(), date) && doctorWorksThatDayAndTime(appointment.getDoctor_id(), date, time)) {
                    System.out.println("Parece que el doctor trabaja ese dia a esa hora");
                    if (!scheduleHaveAppointments(appointment.getDoctor_id(), appointment.getDateHour())) {
                        System.out.println("Parece que paso la validacion.Estoy a un paso de persistirlo...");
                        var updatedEntity = existingEntity;
                        updatedEntity.setDoctor(dRepository.findById(appointment.getDoctor_id()));
                        updatedEntity.setDateHour(appointment.getDateHour());
                        updatedEntity.setQueryReason(appointment.getQueryReason());
                        updatedEntity = entityManager.merge(updatedEntity);
                        return updatedEntity;
                    } else {
                        throw new IllegalArgumentException("The doctor already has an appointment at the specified time or within the 30 minutes before.");
                    }
                }
            }
        
            throw new IllegalArgumentException("The date and time are not valid.");
        }
        return null;
    }

    
    @Transactional
    public boolean delete(Long id) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            repository.delete(existingEntity);
            return true;
        }
        return false;
    }

    //----------------------------LOGICA PARA CREAR EN HORARIO VALIDADO-------------------------------


public boolean isAValidDate(LocalDate date) {
    System.out.println("Ejecuto ValidarDia");
    LocalDate today = LocalDate.now();
    LocalDate futureDate = today.plusDays(60);
    LocalDate earliestValidDate = today.plusDays(1); // Los turnos solo pueden pedirse para el día siguiente en adelante
    return !date.isBefore(earliestValidDate) && !date.isAfter(futureDate);
}

    public boolean scheduleHaveAppointments(Long doctorId, LocalDateTime dateTime) {
        System.out.println("Ejecuto HorarioTieneCita");
        LocalDateTime startTime = dateTime.minusMinutes(30);
        System.out.println(startTime);
        LocalDateTime endTime = dateTime;
    
        List<AppointmentEntity> appointments = repository.findAppointmentsByDoctorAndDate(doctorId, dateTime.toLocalDate());
        for (AppointmentEntity appointment : appointments) {
            LocalDateTime appointmentTime = appointment.getDateHour();
            if (!appointmentTime.isBefore(startTime) && !appointmentTime.isAfter(endTime)) {
                System.out.println("Tiene cita, su cita es:" + appointmentTime);
                return true;
            }
        }
        return false;
    }

    public boolean doctorWorksThatDay(Long doctor_id, LocalDate date){
        System.out.println("Ejecuto doctorTrabaja ese dia");
        DoctorEntity doctor = dRepository.findById(doctor_id);
        if (doctor == null) {
            System.out.println("Doctor no encontrado");
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        Set<ScheduleEntity> schedules = doctor.getSchedules();
        for (ScheduleEntity schedule : schedules) {
            System.out.println(schedule.getDay());
            System.out.println(dayOfWeek);
            if (schedule.getDay().toString().equals(dayOfWeek.toString())) {
                return true;
            }
        }
        return false; // El doctor no trabaja ese día
    }


    public boolean doctorWorksThatDayAndTime(Long doctor_id, LocalDate date, LocalTime time) {
        System.out.println("Ejecuto doctor trabaja ese dia y hora");
        if (doctorWorksThatDay(doctor_id, date)) {
            DoctorEntity doctor = dRepository.findById(doctor_id);
            Set<ScheduleEntity> schedules = doctor.getSchedules();
            for (ScheduleEntity schedule : schedules) {
                if (schedule.getDay().toString().equals(date.getDayOfWeek().toString())) {
                    LocalTime entryTime = schedule.getEntryTime();
                    LocalTime departureTime = schedule.getDepartureTime();
                    if (time.isAfter(entryTime) && time.isBefore(departureTime)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

