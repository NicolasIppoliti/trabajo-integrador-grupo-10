package org.acme.resources;

import org.acme.models.entities.Recipe;
import org.acme.repositories.RecipeRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @Inject
    RecipeRepository recipeRepository;

    @GET
    public List<Recipe> getAll() {
        return recipeRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Recipe recipe = recipeRepository.findById(id);
        if (recipe == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(recipe).build();
    }

    @POST
    @Transactional
    public Response create(Recipe recipe) {
    	recipeRepository.persist(recipe);
        return Response.status(Response.Status.CREATED).entity(recipe).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Recipe recipe) {
        Recipe existingRecipe = recipeRepository.findById(id);
        if (existingRecipe == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingRecipe.setDescription(recipe.getDescription());
        existingRecipe.setAppointment(recipe.getAppointment());
        existingRecipe.setIssueDate(recipe.getIssueDate());
        return Response.ok(existingRecipe).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Recipe existingRecipe = recipeRepository.findById(id);
        if (existingRecipe == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        recipeRepository.delete(existingRecipe);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
