package org.acme.repositories;

import org.acme.models.entities.DoctorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoctorRepository implements PanacheRepository<DoctorEntity> {
}
