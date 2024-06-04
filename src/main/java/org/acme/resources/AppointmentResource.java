/* package org.acme.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import org.acme.entities.Appointment;

@Path("/turnos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    @GET
    public List<Appointment> getAppointments() {
        return Appointment.listAll();
    }

    @POST
    public Appointment createAppointment(Appointment appointment) {
    	appointment.persist();
        return appointment;
    }

    @PUT
    @Path("/{id}")
    public Appointment updateAppointment(@PathParam("id") Long id, Appointment appointment) {
    	Appointment entity = Appointment.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Turno no encontrado", 404);
        }
        entity.patient = appointment.patient;
        entity.dateHour = appointment.dateHour;
        entity.doctor = appointment.doctor;
        entity.queryReason = appointment.queryReason;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deleteAppointment(@PathParam("id") Long id) {
    	Appointment entity = Appointment.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Turno no encontrado", 404);
        }
        entity.delete();
    }
}
 */