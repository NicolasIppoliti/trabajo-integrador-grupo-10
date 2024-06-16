package org.acme.repositories;

import java.util.Set;
import java.util.stream.Collectors;

import org.acme.models.entities.PatientEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepositoryBase<PatientEntity, Long>{

    public PatientEntity findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public Set<Long> findAppointmentIdsByPatientId(Long patientId) {
        return getEntityManager()
                .createQuery("SELECT a.id FROM AppointmentEntity a WHERE a.patient.id = :patientId", Long.class)
                .setParameter("patientId", patientId)
                .getResultStream()
                .collect(Collectors.toSet());
    }
}
