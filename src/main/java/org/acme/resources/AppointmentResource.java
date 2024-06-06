package org.acme.resources;

import org.acme.entities.Appointment;
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
    @Transactional
    public Response getAppointments() {
    	List<Appointment> appointments = appointmentRepository.listAll();
        return Response.ok(appointments).build();
    }
    
    @GET
    @Transactional
    @Path("/{id}")
    public Response getAppointmentById(@PathParam("id") Long id) {
        Appointment appointment = appointmentRepository.findById(id);
        return Response.ok(appointment).build();
    }

    @POST
    @Transactional
    public Response createAppointment(Appointment appointment) {
    	appointment.setId(null);
        appointmentRepository.persist(appointment);
        return Response.status(Response.Status.CREATED).entity(appointment).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateAppointment(@PathParam("id") Long id, Appointment updatedAppointment) {
        Appointment appointment = appointmentRepository.findById(id);
        if (appointment == null) {
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity("Turno no encontrado con el ID: " + id)
        			.build();
        }
        appointment.setPatient(updatedAppointment.getPatient());
        appointment.setDateHour(updatedAppointment.getDateHour());
        appointment.setDoctor(updatedAppointment.getDoctor());
        appointment.setQueryReason(updatedAppointment.getQueryReason());
        updatedAppointment.setId(null);
        appointmentRepository.persist(appointment);
        
        return Response.ok(updatedAppointment).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteAppointment(@PathParam("id") Long id) {
        Appointment appointment = appointmentRepository.findById(id);
        if (appointment == null) {
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity("Turno no encontrado con el ID: " + id)
        			.build();
        }
        appointmentRepository.delete(appointment);
        return Response.noContent().build();
    }
}
