package org.acme.resources;

import org.acme.models.entities.Schedule;
import org.acme.repositories.ScheduleRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleResource {

    @Inject
    ScheduleRepository scheduleRepository;

    @GET
    public List<Schedule> getAll() {
        return scheduleRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(schedule).build();
    }

    @POST
    @Transactional
    public Response create(Schedule schedule) {
    	scheduleRepository.persist(schedule);
        return Response.status(Response.Status.CREATED).entity(schedule).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Schedule schedule) {
        Schedule existingSchedule = scheduleRepository.findById(id);
        if (existingSchedule == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingSchedule.setDay(schedule.getDay());
        existingSchedule.setEntry_time(schedule.getEntry_time());
        existingSchedule.setDeparture_time(schedule.getDeparture_time());
        return Response.ok(existingSchedule).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Schedule existingSchedule = scheduleRepository.findById(id);
        if (existingSchedule == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        scheduleRepository.delete(existingSchedule);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
