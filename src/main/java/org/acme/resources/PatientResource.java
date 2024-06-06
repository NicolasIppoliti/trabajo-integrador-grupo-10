package org.acme.resources;

import org.acme.entities.Patient;
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
    @Transactional
    public Response getPatients() {
        List<Patient> patients = patientRepository.listAll();
        return Response.ok(patients).build();
    }
    
    @GET
    @Transactional
    @Path("/{id}")
    public Response getPatientById(@PathParam("id") Long id) {
    	Patient patient = patientRepository.findById(id);
        return Response.ok(patient).build();
    }

    @POST
    @Transactional
    public Response createPatient(Patient patient) {
    	patient.setId(null);
    	patientRepository.persist(patient);
        return Response.status(Response.Status.CREATED).entity(patient).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updatePatient(@PathParam("id") Long id, Patient updatedPatient) {
    	Patient patient = patientRepository.findById(id);
        if (patient == null) {
        	return Response.status(Response.Status.NOT_FOUND)
                    .entity("Paciente no encontrado con el ID: " + id)
                    .build();
        }
        patient.setFirstName(updatedPatient.getFirstName());
        patient.setLastName(updatedPatient.getLastName());
        patient.setEmail(updatedPatient.getEmail());
        patient.setPassword(updatedPatient.getPassword());
        patient.setPhone(updatedPatient.getPhone());
        updatedPatient.setId(null);
        patientRepository.persist(patient);
        
        return Response.ok(updatedPatient).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deletePatient(@PathParam("id") Long id) {
    	Patient patient = patientRepository.findById(id);
        if (patient == null) {
        	return Response.status(Response.Status.NOT_FOUND)
                    .entity("Paciente no encontrado con el ID: " + id)
                    .build();
        }
        patientRepository.delete(patient);
        return Response.noContent().build();
    }
}
