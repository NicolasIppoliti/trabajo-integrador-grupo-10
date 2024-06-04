package org.acme.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import org.acme.entities.Doctor;

@Path("/especialistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

    @GET
    public List<Doctor> getDoctors() {
        return Doctor.listAll();
    }

    @POST
    public Doctor createDoctor(Doctor doctor) {
    	doctor.persist();
        return doctor;
    }

    @PUT
    @Path("/{id}")
    public Doctor updateDoctor(@PathParam("id") Long id, Doctor doctor) {
    	Doctor entity = Doctor.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Doctor no encontrado", 404);
        }
        entity.name = doctor.name;
        entity.speciality = doctor.speciality;
        entity.schedules = doctor.schedules;
        entity.location = doctor.location;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deleteDoctor(@PathParam("id") Long id) {
    	Doctor entity = Doctor.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Doctor no encontrado", 404);
        }
        entity.delete();
    }
}
