package org.acme.resources;

import org.acme.models.entities.AppointmentEntity;
import org.acme.repositories.AppointmentRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/turnos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    @Inject
    AppointmentRepository appointmentRepository;

    @GET
    public List<AppointmentEntity> getAll() {
        return appointmentRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        AppointmentEntity appointment = appointmentRepository.findById(id);
        if (appointment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(appointment).build();
    }

    @POST
    @Transactional
    public Response create(AppointmentEntity appointment) {
    	appointmentRepository.persist(appointment);
        return Response.status(Response.Status.CREATED).entity(appointment).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, AppointmentEntity appointment) {
        AppointmentEntity existingAppointment = appointmentRepository.findById(id);
        if (existingAppointment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingAppointment.setPatient(appointment.getPatient());
        existingAppointment.setDateHour(appointment.getDateHour());
        existingAppointment.setDoctor(appointment.getDoctor());
        existingAppointment.setQueryReason(appointment.getQueryReason());
        return Response.ok(existingAppointment).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        AppointmentEntity existingAppointment = appointmentRepository.findById(id);
        if (existingAppointment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        appointmentRepository.delete(existingAppointment);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
