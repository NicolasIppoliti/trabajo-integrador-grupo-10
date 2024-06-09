package org.acme.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.acme.models.entities.AppointmentEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AppointmentRepository implements PanacheRepository<AppointmentEntity> {
    @Inject
    EntityManager entityManager;

    @Transactional
    public void delete(AppointmentEntity entity) {
        if (entityManager.contains(entity)) {
            entityManager.remove(entity);
        } else {
            entityManager.remove(entityManager.merge(entity));
        }
    }
}
