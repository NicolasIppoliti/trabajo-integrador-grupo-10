package org.acme.repositories;

import org.acme.models.entities.PatientEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepositoryBase<PatientEntity, Long>{

	public PatientEntity findByEmail(String email){
        return find("email", email).firstResult();

    }
}
