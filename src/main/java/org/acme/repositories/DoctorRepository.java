package org.acme.repositories;

import org.acme.models.entities.DoctorEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DoctorRepository implements PanacheRepositoryBase<DoctorEntity, Long> {

}
