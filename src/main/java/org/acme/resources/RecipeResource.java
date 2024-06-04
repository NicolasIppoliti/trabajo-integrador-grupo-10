package org.acme.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import org.acme.entities.Recipe;

@Path("/recetas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @GET
    public List<Recipe> getRecipes() {
        return Recipe.listAll();
    }

    @POST
    public Recipe createRecipe(Recipe recipe) {
    	recipe.persist();
        return recipe;
    }

    @PUT
    @Path("/{id}")
    public Recipe updateRecipe(@PathParam("id") Long id, Recipe recipe) {
    	Recipe entity = Recipe.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Receta not found", 404);
        }
        entity.description = recipe.description;
        entity.appointment = recipe.appointment;
        entity.issueDate = recipe.issueDate;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deleteReceta(@PathParam("id") Long id) {
    	Recipe entity = Recipe.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Receta not found", 404);
        }
        entity.delete();
    }
}
