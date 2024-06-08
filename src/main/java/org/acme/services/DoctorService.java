package org.acme.services;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.domain.Doctor;
import org.acme.mappers.DoctorMapper;
import org.acme.repositories.DoctorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DoctorService {

    @Inject
    DoctorRepository repository;

    @Inject
    DoctorMapper mapper;

    @Inject
    EntityManager entityManager;

    public List<Doctor> getAll(){
        return repository.listAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    public Doctor getById(Long id) {
        return mapper.toDomain(repository.findById(id));
    }

    @Transactional
    public Doctor create(Doctor doctor) {
        var entity = mapper.toEntity(doctor);
        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    @Transactional
    public Doctor update(Long id, Doctor doctor) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            var updatedEntity = mapper.toEntity(doctor);
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
