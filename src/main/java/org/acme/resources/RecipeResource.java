package org.acme.resources;

import org.acme.models.entities.Recipe;
import org.acme.repositories.RecipeRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/recetas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

	@Inject
	RecipeRepository recipeRepository;
	
    @GET
    @Transactional
    public Response getRecipes() {
        List<Recipe> recipes = recipeRepository.listAll();
        return Response.ok(recipes).build();
    }
    
    @GET
    @Transactional
    @Path("/{id}")
    public Response getBranchById(@PathParam("id") Long id) {
    	Recipe recipe = recipeRepository.findById(id);
        return Response.ok(recipe).build();
    }

    @POST
    @Transactional
    public Response createRecipe(Recipe recipe) {
    	recipe.setId(null);
    	recipeRepository.persist(recipe);
        return Response.status(Response.Status.CREATED).entity(recipe).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateRecipe(@PathParam("id") Long id, Recipe updatedRecipe) {
    	Recipe recipe = recipeRepository.findById(id);
        if (recipe == null) {
        	return Response.status(Response.Status.NOT_FOUND)
                    .entity("Receta no encontrada con el ID: " + id)
                    .build();
        	}
        recipe.setDescription(updatedRecipe.getDescription());
        recipe.setAppointment(updatedRecipe.getAppointment());
        recipe.setIssueDate(updatedRecipe.getIssueDate());
        updatedRecipe.setId(null);
        recipeRepository.persist(recipe);
        
        return Response.ok(recipe).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteReceta(@PathParam("id") Long id) {
    	Recipe recipe = recipeRepository.findById(id);
    	if (recipe == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Receta no encontrada con el ID: " + id)
                           .build();
        }
        recipeRepository.delete(recipe);
        return Response.noContent().build();
    }
}
