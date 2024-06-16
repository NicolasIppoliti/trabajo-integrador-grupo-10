package org.acme.services;

import org.acme.domain.Patient;
import org.acme.mappers.PatientMapper;
import org.acme.repositories.PatientRepository;
import org.acme.security.TokenService;
import org.acme.utils.Role;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PatientService {

    @Inject
    PatientRepository repository;

    @Inject
    PatientMapper mapper;

    @Inject
    TokenService tService;

    @Inject
    EntityManager entityManager;

    public List<Patient> getAll() {
        return repository.listAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    public Patient getById(Long id) {
        return mapper.toDomain(repository.findById(id));
    }

    @Transactional
    public Patient create(Patient patient) {
        var entity = mapper.toEntity(patient);
        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    @Transactional
    public Patient update(Long id, Patient patient) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            var updatedEntity = mapper.toEntity(patient);
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

    @Transactional
    public String RegisterAndGetToken(Patient patient) {
        var entity = mapper.toEntity(patient);
        entity.setRole(Role.PATIENT);
        repository.persist(entity);
        String token = tService.generatePatientToken(entity);
        return token;
    }
}
