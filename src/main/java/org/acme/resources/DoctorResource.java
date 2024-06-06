package org.acme.resources;

import java.util.List;

import org.acme.entities.Doctor;
import org.acme.repositories.DoctorRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;

@Path("/doctors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

	@Inject
	DoctorRepository doctorRepository;
	
    @GET
    public List<Doctor> getDoctors() {
        return doctorRepository.listAll();
    }

    @POST
    public Doctor createDoctor(Doctor doctor) {
    	doctorRepository.persist(doctor);
        return doctor;
    }

    @PUT
    @Path("/{id}")
    public Doctor updateDoctor(@PathParam("id") Long id, Doctor doctor) {
    	Doctor entity = doctorRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Doctor no encontrado", 404);
        }
        entity.setFirstName(doctor.getFirstName());
        entity.setLastName(doctor.getLastName());
        entity.setDni(doctor.getDni());
        entity.setSpeciality(doctor.getSpeciality());
        entity.setBranch(doctor.getBranch());
        doctorRepository.persist(entity);
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deleteDoctor(@PathParam("id") Long id) {
    	Doctor entity = doctorRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Paciente no encontrado", 404);
        }
        doctorRepository.delete(entity);
    }
}
