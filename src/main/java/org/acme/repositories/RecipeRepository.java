package org.acme.repositories;

import org.acme.entities.Recipe;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RecipeRepository implements PanacheRepositoryBase<Recipe, Long>{
	
}
