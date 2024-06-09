package org.acme.repositories;

import java.time.LocalDate;
import java.util.List;

import org.acme.models.entities.AppointmentEntity;


import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class AppointmentRepository implements PanacheRepositoryBase<AppointmentEntity, Long>{

    @Inject
    EntityManager em;

    public List<AppointmentEntity> findAppointmentsByDoctorAndDate(Long doctorId, LocalDate date) {
        return em.createQuery("SELECT a FROM AppointmentEntity a WHERE a.doctor.id = :doctorId AND a.dateHour >= :startDate AND a.dateHour < :endDate", AppointmentEntity.class)
                 .setParameter("doctorId", doctorId)
                 .setParameter("startDate", date.atStartOfDay())
                 .setParameter("endDate", date.plusDays(1).atStartOfDay())
                 .getResultList();
    }
    
	
}
