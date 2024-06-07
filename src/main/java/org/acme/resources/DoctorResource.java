package org.acme.resources;

import org.acme.models.entities.Doctor;
import org.acme.repositories.DoctorRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/doctors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

    @Inject
    DoctorRepository doctorRepository;

    @GET
    public List<Doctor> getAll() {
        return doctorRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Doctor doctor = doctorRepository.findById(id);
        if (doctor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(doctor).build();
    }

    @POST
    @Transactional
    public Response create(Doctor doctor) {
    	doctorRepository.persist(doctor);
        return Response.status(Response.Status.CREATED).entity(doctor).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id);
        if (existingDoctor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingDoctor.setFirstName(doctor.getFirstName());
        existingDoctor.setLastName(doctor.getLastName());
        existingDoctor.setDni(doctor.getDni());
        existingDoctor.setSpeciality(doctor.getSpeciality());
        existingDoctor.setSchedules(doctor.getSchedules());
        existingDoctor.setBranch(doctor.getBranch());
        return Response.ok(existingDoctor).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Doctor existingDoctor = doctorRepository.findById(id);
        if (existingDoctor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        doctorRepository.delete(existingDoctor);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
