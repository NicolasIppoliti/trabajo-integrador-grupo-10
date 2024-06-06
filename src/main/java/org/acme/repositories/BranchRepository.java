package org.acme.repositories;

import org.acme.models.entities.Branch;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BranchRepository implements PanacheRepositoryBase<Branch, Long>{
    
}
