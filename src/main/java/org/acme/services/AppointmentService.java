package org.acme.services;

import org.acme.domain.Appointment;
import org.acme.domain.AppointmentResponseDTO;
import org.acme.models.entities.ScheduleEntity;
import org.acme.mappers.AppointmentMapper;
import org.acme.mappers.AppointmentResponseMapper;
import org.acme.models.entities.AppointmentEntity;
import org.acme.models.entities.DoctorEntity;
import org.acme.models.entities.PatientEntity;
import org.acme.repositories.AppointmentRepository;
import org.acme.repositories.DoctorRepository;
import org.acme.repositories.PatientRepository;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    AppointmentResponseMapper rMapper;

    @Inject
    EntityManager entityManager;

    public List<AppointmentResponseDTO> getAll() {
        return repository.listAll().stream().map(rMapper::toDomain).collect(Collectors.toList());
    }

    public List<AppointmentResponseDTO> getByPatientId(Long patientId){
        return repository.findAppointmentsByPatientId(patientId).stream().map(rMapper::toDomain).collect(Collectors.toList());
    }

    public AppointmentResponseDTO getById(Long id) {
        return rMapper.toDomain(repository.findById(id));
    }

    @Transactional
    public AppointmentEntity create(Appointment appointment) {
        DoctorEntity doctor = dRepository.findById(appointment.getDoctor_id());
        PatientEntity patient = pRepository.findById(appointment.getPatient_id());

        LocalDateTime dateTime = appointment.getDateHour();
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        AppointmentEntity entity = new AppointmentEntity();
        entity.setDateHour(LocalDateTime.of(date, time));
        entity.setPatient(patient);
        entity.setDoctor(doctor);
        entity.setQueryReason(appointment.getQueryReason());
        System.out.println(date);
        System.out.println(time);

        if (isAValidDate(date) && isAValidHour(time)) {
            if (doctorWorksThatDay(appointment.getDoctor_id(), date)
                    && doctorWorksThatDayAndTime(appointment.getDoctor_id(), date, time)) {
                if (!scheduleHaveAppointments(appointment.getDoctor_id(), dateTime)) {
                    repository.persist(entity);
                    return entity;
                } else {
                    throw new IllegalArgumentException(
                            "The doctor already has an appointment at the specified time or within the 30 minutes before.");
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

            if (isAValidDate(date) && isAValidHour(time)) {
                if (doctorWorksThatDay(appointment.getDoctor_id(), date)
                        && doctorWorksThatDayAndTime(appointment.getDoctor_id(), date, time)) {
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
                        throw new IllegalArgumentException(
                                "The doctor already has an appointment at the specified time or within the 30 minutes before.");
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

    // ----------------------------LOGICA PARA CREAR EN HORARIO VALIDADO-------------------------------

    public boolean isAValidDate(LocalDate date) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusMonths(2);
        LocalDate earliestValidDate = today.plusDays(1); // Los turnos solo pueden pedirse para el día siguiente en
                                                         // adelante
        return !date.isBefore(earliestValidDate) && !date.isAfter(futureDate);
    }

    public boolean isAValidHour(LocalTime time) {
        int minutes = time.getMinute();
        int seconds = time.getSecond();
        Log.warn("AQUI LOS MINUTOS Y SEGUNDOS!");
        System.out.println(minutes + seconds);

        // Verificar que los minutos sean 0 o 30, y que los segundos sean 0
        return (minutes == 0 || minutes == 30) && seconds == 0;
    }

    public boolean scheduleHaveAppointments(Long doctorId, LocalDateTime dateTime) {
        // Obtener la hora exacta sin segundos
        LocalDateTime roundedDateTime = dateTime.withSecond(0).withNano(0);

        List<AppointmentEntity> appointments = repository.findAppointmentsByDoctorAndDate(doctorId,
                dateTime.toLocalDate());
        for (AppointmentEntity appointment : appointments) {
            LocalDateTime appointmentTime = appointment.getDateHour().withSecond(0).withNano(0);

            // Verificar si ya existe un turno en la hora exacta
            if (appointmentTime.equals(roundedDateTime)) {
                return true; // Ya existe un turno en esa hora exacta
            }
        }

        return false; // No existe un turno en esa hora exacta
    }

    public boolean doctorWorksThatDay(Long doctor_id, LocalDate date) {
        DoctorEntity doctor = dRepository.findById(doctor_id);
        if (doctor == null) {
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

// ----------------------------LOGICA PARA MAPEAR TURNOS DISPONIBLES-------------------------------
    public List<Map<String, Object>> generateDoctorAvailability(Long doctorId) {
        Set<ScheduleEntity> schedules = dRepository.findSchedulesByDoctorId(doctorId);

        // Obtener fechas para los próximos 2 meses
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = startDate.plusMonths(2);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Map<String, Object>> doctorAvailabilityList = new ArrayList<>();

        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            // Mapa para almacenar la disponibilidad del doctor para este día
            Map<String, Object> doctorAvailability = new HashMap<>();
            doctorAvailability.put("date", date.format(dateFormatter));

            // Lista para almacenar los slots disponibles para este día
            List<String> slots = new ArrayList<>();

            // Iterar sobre los horarios del doctor para encontrar los slots disponibles
            for (ScheduleEntity schedule : schedules) {
                String dayOfWeek = schedule.getDay().toString();
                LocalTime startTime = schedule.getEntryTime();
                LocalTime endTime = schedule.getDepartureTime().minusMinutes(30);

                // Verificar si el día de la semana coincide con el día actual
                if (date.getDayOfWeek().toString() == dayOfWeek) {
                    // Generar slots de horas terminadas en 00 mins o 30 mins y 00 segundos
                    LocalDateTime dateTime = LocalDateTime.of(date, startTime);
                    while (dateTime.plusMinutes(30).isBefore(LocalDateTime.of(date, endTime))) {
                        if (dateTime.getMinute() == 0 || dateTime.getMinute() == 30) {
                            slots.add(dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                        }
                        dateTime = dateTime.plusMinutes(30);
                    }
                }
            }

            // Agregar los slots disponibles al mapa de disponibilidad del doctor
            doctorAvailability.put("slots", slots);

            // Si hay slots disponibles para este día, agregar al resultado final
            if (!slots.isEmpty()) {
                doctorAvailabilityList.add(doctorAvailability);
            }

            // Avanzar al siguiente día
            date = date.plusDays(1);
        }

        return doctorAvailabilityList;
    }

    public List<Map<String, Object>> getAvailableSlots(Long doctorId) {
        // Obtener los horarios ocupados del médico
        Set<LocalDateTime> occupiedSlots = dRepository.findAppointmentDateHourByDoctorId(doctorId);

        // Obtener la disponibilidad de horarios para el médico
        List<Map<String, Object>> availability = generateDoctorAvailability(doctorId);

        // Filtrar la disponibilidad para obtener los horarios no ocupados
        List<Map<String, Object>> availableSlots = new ArrayList<>();

        for (Map<String, Object> doctorAvailability : availability) {
            String date = (String) doctorAvailability.get("date");
            @SuppressWarnings("unchecked")
            List<String> slots = (List<String>) doctorAvailability.get("slots");

            List<String> available = new ArrayList<>();
            for (String slot : slots) {
                LocalDateTime slotDateTime = LocalDateTime.parse(date + "T" + slot + ":00");
                if (!occupiedSlots.contains(slotDateTime)) {
                    available.add(slot);
                }
            }

            if (!available.isEmpty()) {
                Map<String, Object> availableSlotsMap = new HashMap<>();
                availableSlotsMap.put("date", date);
                availableSlotsMap.put("slots", available);
                availableSlots.add(availableSlotsMap);
            }
        }

        return availableSlots;
    }

}
