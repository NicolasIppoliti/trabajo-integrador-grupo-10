package org.acme.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import org.acme.entities.Patient;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @GET
    public List<Patient> getPatients() {
        return Patient.listAll();
    }

    @POST
    public Patient createPatient(Patient patient) {
    	patient.persist();
        return patient;
    }

    @PUT
    @Path("/{id}")
    public Patient updatePatient(@PathParam("id") Long id, Patient patient) {
    	Patient entity = Patient.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Paciente no encontrado", 404);
        }
        entity.firstName = patient.firstName;
        entity.lastName = patient.lastName;
        entity.email = patient.email;
        entity.phone = patient.phone;
        entity.persist();
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deletePatient(@PathParam("id") Long id) {
    	Patient entity = Patient.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Paciente no encontrado", 404);
        }
        entity.delete();
    }
}
