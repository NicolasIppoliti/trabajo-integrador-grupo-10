package org.acme.resources;

import java.util.List;

import org.acme.models.entities.Schedule;
import org.acme.repositories.ScheduleRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/horarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ScheduleResource {

	@Inject
	ScheduleRepository scheduleRepository;
	
    @GET
    @Transactional
    public Response getSchedules() {
        List<Schedule> schedules = scheduleRepository.listAll();
        return Response.ok(schedules).build();
    }

    @GET
    @Transactional
    @Path("/{id}")
    public Response getScheduleById(@PathParam("id") Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        return Response.ok(schedule).build();
    }


    @POST
    @Transactional
    public Response createSchedule(Schedule schedule) {
        schedule.setId(null);
        scheduleRepository.persist(schedule);
        return Response.status(Response.Status.CREATED).entity(schedule).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateSchedule(@PathParam("id") Long id, Schedule updatedSchedule) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("schedule not found with id " + id)
                           .build();
        }
        schedule.setDay(updatedSchedule.getDay());
        schedule.setEntry_time(updatedSchedule.getEntry_time());
        schedule.setDeparture_time(updatedSchedule.getDeparture_time());
        updatedSchedule.setId(null);
        scheduleRepository.persist(schedule);

        return Response.ok(schedule).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteSchedule(@PathParam("id") Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Schedule not found with id " + id)
                           .build();
        }
        scheduleRepository.delete(schedule);
        return Response.noContent().build();
    }
}