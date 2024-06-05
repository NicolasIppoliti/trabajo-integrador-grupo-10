package org.acme.repositories;

import org.acme.entities.Branch;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BranchRepository implements PanacheRepository<Branch> {
	
}
