package org.acme.resources;

import org.acme.domain.Appointment;
import org.acme.models.entities.AppointmentEntity;
import org.acme.services.AppointmentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/turnos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    @Inject
    AppointmentService service;

    @GET
    public Response getAll() {
        try {
            List<Appointment> appointments = service.getAll();
            return Response.ok(appointments).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener los turnos").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            Appointment appointment = service.getById(id);
            if (appointment != null) {
                return Response.ok(appointment).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Turno no encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener el turno").build();
        }
    }

    @POST
    public Response create(Appointment appointment) {
        try {
            AppointmentEntity created = service.create2(appointment);
            return Response.status(Response.Status.CREATED).entity(created).build();    //TODO! Ya creo y persistio el turno, pero a veces no devuelve el build al endpoint.
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al crear el turno").build();
        }
    }

    // @PUT     //TODO! Corregir!
    // @Path("/{id}")
    // public Response update(@PathParam("id") Long id, Appointment appointment) {
    //     try {
    //         Appointment updated = service.update(id, appointment);
    //         if (updated != null) {
    //             return Response.ok(updated).build();
    //         } else {
    //             return Response.status(Response.Status.NOT_FOUND).entity("Turno no encontrado").build();
    //         }
    //     } catch (Exception e) {
    //         return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar el turno").build();
    //     }
    // }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            boolean deleted = service.delete(id);
            if (deleted) {
                return Response.noContent().build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Turno no encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el turno").build();
        }
    }

    
}
