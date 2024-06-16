package org.acme.services;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.domain.Branch;
import org.acme.mappers.BranchMapper;
import org.acme.models.entities.BranchEntity;
import org.acme.repositories.BranchRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BranchService {
@Inject
    BranchRepository repository;

    @Inject
    BranchMapper mapper;
    
    @Inject
    EntityManager entityManager;

    public List<Branch> getAll() {
        return repository.listAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    public Branch getById(Long id) {
        return mapper.toDomain(repository.findById(id));
    }

    public BranchEntity getBranchById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Branch create(Branch branch) {
        var entity = mapper.toEntity(branch);
        entity = entityManager.merge(entity);
        repository.persist(entity);
        System.out.println("Entidad por salir del create" + entity);
        Branch createdBranch = mapper.toDomain(entity);
    System.out.println("Created Branch: " + createdBranch);
        return mapper.toDomain(entity);
    }

    @Transactional
    public Branch update(Long id, Branch branch) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            var updatedEntity = mapper.toEntity(branch);
            updatedEntity.setId(id);
            updatedEntity = entityManager.merge(updatedEntity);
            return mapper.toDomain(updatedEntity);
        }
        return null;
    }

    @Transactional
    public boolean delete(Long id) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            repository.delete(existingEntity);
            return true;
        }
        return false;
    }
}
