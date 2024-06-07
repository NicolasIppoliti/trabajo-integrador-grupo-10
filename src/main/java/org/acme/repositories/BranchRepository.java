package org.acme.repositories;

import org.acme.models.entities.BranchEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BranchRepository implements PanacheRepositoryBase<BranchEntity, Long>{
    
}
