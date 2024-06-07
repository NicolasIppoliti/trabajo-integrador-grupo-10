package org.acme.resources;

import org.acme.domain.Recipe;
import org.acme.services.RecipeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @Inject
    RecipeService service;

    @GET
    public List<Recipe> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Recipe recipe = service.getById(id);
        if (recipe == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(recipe).build();
    }

    @POST
    public Response create(Recipe recipe) {
        return Response.status(Response.Status.CREATED).entity(service.create(recipe)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Recipe recipe) {
        Recipe updatedRecipe = service.update(id, recipe);
        if (updatedRecipe == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedRecipe).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (service.delete(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
