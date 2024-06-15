package org.acme.resources;

import org.acme.domain.Recipe;
import org.acme.models.entities.RecipeEntity;
import org.acme.repositories.AppointmentRepository;
import org.acme.services.AppointmentService;
import org.acme.services.RecipeService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/recetas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @Inject
    RecipeService service;

    @Inject
    AppointmentRepository aRepository;

    @GET
    public Response getAll() {
        try {
            List<Recipe> recipes = service.getAll();
            return Response.ok(recipes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener las recetas").build();
        }
    }

    @GET
    @Path("/receta/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            Recipe recipe = service.getById(id);
            if (recipe == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Receta no encontrada").build();
            }
            return Response.ok(recipe).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener la receta").build();
        }
    }

    @POST
    public Response create(Recipe recipe) {
        try {
            Recipe createdRecipe = service.create(recipe);
            return Response.status(Response.Status.CREATED).entity(createdRecipe).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al crear la receta: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/receta/{id}")
    public Response update(@PathParam("id") Long id, Recipe recipe) {
        try {
            Recipe updatedRecipe = service.update(id, recipe);
            if (updatedRecipe == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Receta no encontrada").build();
            }
            return Response.ok(updatedRecipe).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar la receta").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (service.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Receta no encontrada").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar la receta").build();
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("AUTHORIZED_PATIENT")
    public Response getByAppointmentId(@PathParam("id") Long id){
        try {
            Set<RecipeEntity> recipes = aRepository.findRecipesByAppointmentId(id);
            return Response.ok(recipes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener las recetas").build();
        }
    }
}
