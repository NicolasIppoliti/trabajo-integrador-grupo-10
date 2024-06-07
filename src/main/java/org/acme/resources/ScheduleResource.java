package org.acme.resources;

import org.acme.domain.Schedule;
import org.acme.services.ScheduleService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/horarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleResource {

    @Inject
    ScheduleService service;

    @GET
    public List<Schedule> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Schedule schedule = service.getById(id);
        if (schedule == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(schedule).build();
    }

    @POST
    public Response create(Schedule schedule) {
        try {
            Schedule createdSchedule = service.create(schedule);
            return Response.status(Response.Status.CREATED).entity(createdSchedule).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Schedule schedule) {
        Schedule updatedSchedule = service.update(id, schedule);
        if (updatedSchedule == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedSchedule).build();
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
