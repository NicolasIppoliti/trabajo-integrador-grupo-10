package org.acme.repositories;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acme.models.entities.AppointmentEntity;
import org.acme.models.entities.RecipeEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class AppointmentRepository implements PanacheRepositoryBase<AppointmentEntity, Long> {

    @Inject
    EntityManager em;

    public List<AppointmentEntity> findAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        return em.createQuery(
                "SELECT a FROM AppointmentEntity a WHERE a.doctor.id = :doctorId AND a.dateHour >= :startDate AND a.dateHour < :endDate",
                AppointmentEntity.class)
                .setParameter("doctorId", doctorId)
                .setParameter("startDate", date.atStartOfDay())
                .setParameter("endDate", date.plusDays(1).atStartOfDay())
                .getResultList();
    }

    public List<AppointmentEntity> findAppointmentsByDoctor(Long doctorId) {
        return em
                .createQuery("SELECT a FROM AppointmentEntity a WHERE a.doctor.id = :doctorId", AppointmentEntity.class)
                .setParameter("doctorId", doctorId)
                .getResultList();
    }

    public List<AppointmentEntity> findAppointmentsByPatientId(Long patientId) {
        return em
                .createQuery("SELECT a FROM AppointmentEntity a WHERE a.patient.id = :patientId",
                        AppointmentEntity.class)
                .setParameter("patientId", patientId)
                .getResultList();
    }

    public Set<RecipeEntity> findRecipesByAppointmentId(Long appointmentId) {
        return new HashSet<>(
                em.createQuery("SELECT r FROM RecipeEntity r WHERE r.appointment.id = :appointmentId",
                        RecipeEntity.class)
                        .setParameter("appointmentId", appointmentId)
                        .getResultList());
    }

}
