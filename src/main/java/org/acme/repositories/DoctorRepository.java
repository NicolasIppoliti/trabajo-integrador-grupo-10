package org.acme.repositories;

import java.util.List;
import java.util.Set;

import org.acme.entities.Branch;
import org.acme.entities.Doctor;
import org.acme.entities.Schedule;
import org.acme.utils.Speciality;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoctorRepository implements PanacheRepositoryBase<Doctor, Long> {


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

    public List<Doctor> findByBranch(Branch branch) {
        return find("branch", branch).list();
    }
}
