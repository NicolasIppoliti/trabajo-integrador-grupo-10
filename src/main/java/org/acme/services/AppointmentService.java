package org.acme.services;

import org.acme.domain.Appointment;
import org.acme.mappers.AppointmentMapper;
import org.acme.repositories.AppointmentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AppointmentService {

    @Inject
    AppointmentRepository repository;

    @Inject
    AppointmentMapper mapper;

    @Inject
    EntityManager entityManager;

    public List<Appointment> getAll() {
        return repository.listAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    public Appointment getById(Long id) {
        return mapper.toDomain(repository.findById(id));
    }

    @Transactional
    public Appointment create(Appointment appointment) {
        var entity = mapper.toEntity(appointment);
        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    @Transactional
    public Appointment update(Long id, Appointment appointment) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            var updatedEntity = mapper.toEntity(appointment);
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
