package org.acme.resources;

import java.util.List;

import org.acme.entities.Schedule;
import org.acme.repositories.ScheduleRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;

public class ScheduleResource {

	@Inject
	ScheduleRepository scheduleRepository;
	
    @GET
    public List<Schedule> getSchedules() {
        return scheduleRepository.listAll();
    }

    @POST
    public Schedule createSchedule(Schedule schedule) {
    	scheduleRepository.persist(schedule);
        return schedule;
    }

    @PUT
    @Path("/{id}")
    public Schedule updateSchedule(@PathParam("id") Long id, Schedule schedule) {
    	Schedule entity = scheduleRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Horario no encontrado", 404);
        }
        entity.setDay(schedule.getDay());
        entity.setEntry_time(schedule.getEntry_time());
        entity.setDeparture_time(schedule.getDeparture_time());
        scheduleRepository.persist(entity);
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deleteSchedule(@PathParam("id") Long id) {
    	Schedule entity = scheduleRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Horario no encontrado", 404);
        }
        scheduleRepository.delete(entity);
    }
}

