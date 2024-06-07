package org.acme.resources;

import org.acme.domain.Recipe;
import org.acme.services.RecipeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/recetas")
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
    public Recipe getById(@PathParam("id") Long id) {
        return service.getById(id);
    }

    @POST
    public Response create(Recipe recipe) {
        Recipe created = service.create(recipe);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Recipe update(@PathParam("id") Long id, Recipe recipe) {
        return service.update(id, recipe);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
