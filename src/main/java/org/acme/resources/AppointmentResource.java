package org.acme.resources;

import org.acme.entities.Appointment;
import org.acme.repositories.AppointmentRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/appointments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    @Inject
    AppointmentRepository appointmentRepository;

    @GET
    public List<Appointment> getAppointments() {
        return appointmentRepository.listAll();
    }

    @POST
    public Appointment createAppointment(Appointment appointment) {
        appointmentRepository.persist(appointment);
        return appointment;
    }

    @PUT
    @Path("/{id}")
    public Appointment updateAppointment(@PathParam("id") Long id, Appointment appointment) {
        Appointment entity = appointmentRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Appointment not found", 404);
        }
        entity.setPatient(appointment.getPatient());
        entity.setDateHour(appointment.getDateHour());
        entity.setDoctor(appointment.getDoctor());
        entity.setQueryReason(appointment.getQueryReason());
        appointmentRepository.persist(entity);
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deleteAppointment(@PathParam("id") Long id) {
        Appointment entity = appointmentRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Appointment not found", 404);
        }
        appointmentRepository.delete(entity);
    }
}
