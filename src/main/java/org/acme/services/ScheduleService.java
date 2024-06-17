package org.acme.services;

import org.acme.domain.Schedule;
import org.acme.mappers.ScheduleMapper;
import org.acme.repositories.ScheduleRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ScheduleService {

    @Inject
    ScheduleRepository repository;

    @Inject
    ScheduleMapper mapper;
    
    @Inject
    EntityManager entityManager;

    public List<Schedule> getAll() {
        return repository.listAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    public Schedule getById(Long id) {
        return mapper.toDomain(repository.findById(id));
    }

    @Transactional
    public Schedule create(Schedule schedule) {
        var entity = mapper.toEntity(schedule);
        entity = entityManager.merge(entity);
        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    @Transactional
    public Schedule update(Long id, Schedule schedule) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            var updatedEntity = mapper.toEntity(schedule);
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
