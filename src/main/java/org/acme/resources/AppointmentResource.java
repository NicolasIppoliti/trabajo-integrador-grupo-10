package org.acme.resources;

import org.acme.domain.Appointment;
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
    public List<Appointment> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Appointment appointment = service.getById(id);
        if (appointment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(appointment).build();
    }

    @POST
    public Response create(Appointment appointment) {
        return Response.status(Response.Status.CREATED).entity(service.create(appointment)).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Appointment appointment) {
        Appointment updatedAppointment = service.update(id, appointment);
        if (updatedAppointment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedAppointment).build();
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
