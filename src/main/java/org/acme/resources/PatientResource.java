package org.acme.resources;

import org.acme.models.entities.Patient;
import org.acme.repositories.PatientRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @Inject
    PatientRepository patientRepository;

    @GET
    public List<Patient> getAll() {
        return patientRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Patient patient = patientRepository.findById(id);
        if (patient == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(patient).build();
    }

    @POST
    @Transactional
    public Response create(Patient patient) {
    	patientRepository.persist(patient);
        return Response.status(Response.Status.CREATED).entity(patient).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Patient patient) {
        Patient existingPatient = patientRepository.findById(id);
        if (existingPatient == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingPatient.setFirstName(patient.getFirstName());
        existingPatient.setLastName(patient.getLastName());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setPassword(patient.getPassword());
        existingPatient.setPhone(patient.getPhone());
        return Response.ok(existingPatient).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Patient existingPatient = patientRepository.findById(id);
        if (existingPatient == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        patientRepository.delete(existingPatient);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
