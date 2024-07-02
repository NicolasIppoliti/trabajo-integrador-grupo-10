package org.acme.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.utils.Speciality;

@Path("/especialidades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SpecialitiesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        try {
            Speciality[] specialties = Speciality.values();
            return Response.ok(specialties).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener las especialidades")
                    .build();
        }
    }
}
