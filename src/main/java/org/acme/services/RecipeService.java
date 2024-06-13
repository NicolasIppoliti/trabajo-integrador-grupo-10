package org.acme.services;

import org.acme.domain.Recipe;
import org.acme.mappers.RecipeMapper;
import org.acme.models.entities.AppointmentEntity;
import org.acme.repositories.AppointmentRepository;
import org.acme.repositories.RecipeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecipeService {

    @Inject
    RecipeRepository repository;

    @Inject
    RecipeMapper mapper;

    @Inject
    EntityManager entityManager;
    @Inject
    AppointmentRepository apRepository;

    public List<Recipe> getAll() {
        return repository.listAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    public Recipe getById(Long id) {
        return mapper.toDomain(repository.findById(id));
    }

    @Transactional
    public Recipe create(Recipe recipe) {
        AppointmentEntity appointment = apRepository.findById(recipe.getAppointment_id());
        var entity = mapper.toEntity(recipe);
        entity.setAppointment(appointment);
        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    @Transactional
    public Recipe update(Long id, Recipe recipe) {
        AppointmentEntity appointment = apRepository.findById(recipe.getAppointment_id());
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            var updatedEntity = mapper.toEntity(recipe);
            updatedEntity.setId(id);
            updatedEntity.setAppointment(appointment);
            updatedEntity = entityManager.merge(updatedEntity); // Use entityManager to merge
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
