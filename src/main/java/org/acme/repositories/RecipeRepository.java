package org.acme.repositories;

import org.acme.models.entities.RecipeEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RecipeRepository implements PanacheRepository<RecipeEntity>{
	
}
