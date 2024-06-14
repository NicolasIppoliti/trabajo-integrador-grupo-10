package org.acme.resources;

import org.acme.domain.Schedule;
import org.acme.services.ScheduleService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/horarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleResource {

    @Inject
    ScheduleService service;

    @GET
    public Response getAll() {
        try {
            List<Schedule> schedules = service.getAll();
            return Response.ok(schedules).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener los horarios").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            Schedule schedule = service.getById(id);
            if (schedule == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Horario no encontrado").build();
            }
            return Response.ok(schedule).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener el horario").build();
        }
    }

    @POST
    public Response create(@Valid Schedule schedule) {
        try {
            Schedule createdSchedule = service.create(schedule);
            return Response.status(Response.Status.CREATED).entity(createdSchedule).build();
        } catch (ConstraintViolationException e) {
            String violations = e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,@Valid Schedule schedule) {
        try {
            Schedule updatedSchedule = service.update(id, schedule);
            if (updatedSchedule == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Horario no encontrado").build();
            }
            return Response.ok(updatedSchedule).build();
        } catch (ConstraintViolationException e) {
            String violations = e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (service.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Horario no encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el horario").build();
        }
    }
}
