package org.acme.resources;

import org.acme.services.RecipeService;
import org.acme.models.dto.RecipeDTO;
import org.acme.models.dto.ResponseMessage;
import org.acme.mappers.RecipeMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/recetas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @Inject
    RecipeService recipeService;

    @GET
    public List<RecipeDTO> listAll() {
        return recipeService.listAll().stream()
            .map(RecipeMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }
    
    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id) {
        RecipeDTO recipe = RecipeMapper.INSTANCE.toDTO(recipeService.findById(id));
        if (recipe == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity(new ResponseMessage("Receta no encontrada"))
                           .build();
        }
        return Response.ok(recipe).build();
    }

    @POST
    public Response add(RecipeDTO recipeDTO) {
        try {
            recipeService.add(RecipeMapper.INSTANCE.toEntity(recipeDTO));
            return Response.status(Response.Status.CREATED)
                           .entity(new ResponseMessage("Receta creada exitosamente"))
                           .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new ResponseMessage("Error al crear la receta: " + e.getMessage()))
                           .build();
        }
    }

//	  No es necesario para la entrega

//    @PUT
//    @Path("{id}")
//    public Response update(@PathParam("id") Long id, RecipeDTO recipeDTO) {
//        RecipeDTO existing = RecipeMapper.INSTANCE.toDTO(recipeService.findById(id));
//        if (existing == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                           .entity(new ResponseMessage("Receta no encontrada"))
//                           .build();
//        }
//        try {
//            recipeService.update(RecipeMapper.INSTANCE.toEntity(recipeDTO));
//            return Response.ok(new ResponseMessage("Receta actualizada exitosamente")).build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                           .entity(new ResponseMessage("Error al actualizar la receta: " + e.getMessage()))
//                           .build();
//        }
//    }
//
//    @DELETE
//    @Path("{id}")
//    public Response delete(@PathParam("id") Long id) {
//        RecipeDTO existing = RecipeMapper.INSTANCE.toDTO(recipeService.findById(id));
//        if (existing == null) {
//            return Response.status(Response.Status.NOT_FOUND)
//                           .entity(new ResponseMessage("Receta no encontrada"))
//                           .build();
//        }
//        try {
//            recipeService.delete(id);
//            return Response.noContent().build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                           .entity(new ResponseMessage("Error al eliminar la receta: " + e.getMessage()))
//                           .build();
//        }
//    }
}
