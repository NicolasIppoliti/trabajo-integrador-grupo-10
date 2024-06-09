package org.acme.services;

import org.acme.repositories.AppointmentRepository;
import org.acme.repositories.RecipeRepository;
import org.acme.models.entities.AppointmentEntity;
import org.acme.models.entities.RecipeEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class RecipeService {

    @Inject
    RecipeRepository recipeRepository;
    
    @Inject
    AppointmentRepository appointmentRepository;

    public List<RecipeEntity> listAll() {
        return recipeRepository.listAll();
    }
    
    public RecipeEntity findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Transactional
    public void add(RecipeEntity recipeEntity, Long appointmentId) {
    	AppointmentEntity appointment = appointmentRepository.findById(appointmentId);
        
    	if(appointment !=null) {
    		recipeEntity.setAppointment(appointment);
    		recipeRepository.persist(recipeEntity);
    	} else {
    		throw new IllegalArgumentException("Turno no encontrado");
    	}
    }
    
//	  No es necesario para la entrega

//    @Transactional
//    public void update(RecipeEntity recipe) {
//        recipeRepository.getEntityManager().merge(recipe);
//    }
//
//    @Transactional
//    public void delete(Long id) {
//        recipeRepository.deleteById(id);
//    }
}
