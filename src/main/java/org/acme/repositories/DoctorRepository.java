package org.acme.repositories;

import java.util.List;
import java.util.Set;

import org.acme.entities.Doctor;
import org.acme.entities.Schedule;
import org.acme.utils.Speciality;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoctorRepository implements PanacheRepository<Doctor> {


public List<Doctor> findByName(String name) {
        return find("name", name).list();
    }

    public List<Doctor> findBySurname(String surname) {
        return find("surname", surname).list();
    }

    public Doctor findByDni(int dni) {
        return find("dni", dni).firstResult();
    }

    public List<Doctor> findBySpeciality(Speciality speciality) {
        return find("speciality", speciality).list();
    }

    public Set<Schedule> findSchedulesByDoctorId(Long doctorId) {
        Doctor doctor = findById(doctorId);
        if (doctor != null) {
            return doctor.getSchedules();
        }
        return null;
    }
}
