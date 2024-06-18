package org.acme.repositories;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.models.entities.AppointmentEntity;
import org.acme.models.entities.BranchEntity;
import org.acme.models.entities.DoctorEntity;
import org.acme.models.entities.ScheduleEntity;
import org.acme.utils.Day;
import org.acme.utils.Speciality;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoctorRepository implements PanacheRepositoryBase<DoctorEntity, Long> {

    public DoctorEntity findByDni(int dni) {
        return find("dni", dni).firstResult();
    }

    public List<DoctorEntity> findBySpeciality(Speciality speciality) {
        return find("speciality", speciality).list();
    }

    public Set<ScheduleEntity> findSchedulesByDoctorId(Long doctorId) {
        DoctorEntity doctor = findById(doctorId);
        if (doctor != null) {
            return doctor.getSchedules();
        }
        return null;
    }

    public List<DoctorEntity> findByBranch(BranchEntity branch) {
        return find("branch", branch).list();
    }

    public List<DoctorEntity> findAppointments(AppointmentEntity appointments) {
        return find("appointments", appointments).list();
    }

    public Set<Day> findScheduleDaysByDoctorId(Long doctorId) {
        DoctorEntity doctor = findById(doctorId);
        if (doctor != null) {
            return doctor.getSchedules().stream()
                    .map(ScheduleEntity::getDay)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public Set<LocalDateTime> findAppointmentDateHourByDoctorId(Long doctorId) {
        DoctorEntity doctor = findById(doctorId);
        if (doctor != null) {
            return doctor.getAppointments().stream()
                    .map(AppointmentEntity::getDateHour)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }
}
