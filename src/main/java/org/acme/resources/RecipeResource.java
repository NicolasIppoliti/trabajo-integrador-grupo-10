package org.acme.resources;

import org.acme.entities.Recipe;
import org.acme.repositories.RecipeRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/recetas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

	@Inject
	RecipeRepository recipeRepository;
	
    @GET
    public List<Recipe> getRecipes() {
        return recipeRepository.listAll();
    }

    @POST
    public Recipe createRecipe(Recipe recipe) {
    	recipeRepository.persist(recipe);
        return recipe;
    }

    @PUT
    @Path("/{id}")
    public Recipe updateRecipe(@PathParam("id") Long id, Recipe recipe) {
    	Recipe entity = recipeRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Receta not found", 404);
        }
        entity.setDescription(recipe.getDescription());
        entity.setAppointment(recipe.getAppointment());
        entity.setIssueDate(recipe.getIssueDate());
        recipeRepository.persist(entity);
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deleteReceta(@PathParam("id") Long id) {
    	Recipe entity = recipeRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Receta not found", 404);
        }
        recipeRepository.delete(entity);
    }
}
