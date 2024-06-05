package org.acme.resources;

import org.acme.entities.Patient;
import org.acme.repositories.PatientRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

	@Inject
	PatientRepository patientRepository;
	
    @GET
    public List<Patient> getPatients() {
        return patientRepository.listAll();
    }

    @POST
    public Patient createPatient(Patient patient) {
    	patientRepository.persist(patient);
        return patient;
    }

    @PUT
    @Path("/{id}")
    public Patient updatePatient(@PathParam("id") Long id, Patient patient) {
    	Patient entity = patientRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Paciente no encontrado", 404);
        }
        entity.setFirstName(patient.getFirstName());
        entity.setLastName(patient.getLastName());
        entity.setEmail(patient.getEmail());
        entity.setPassword(patient.getPassword());
        entity.setPhone(patient.getPhone());
        patientRepository.persist(entity);
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deletePatient(@PathParam("id") Long id) {
    	Patient entity = patientRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Paciente no encontrado", 404);
        }
        patientRepository.delete(entity);
    }
}
