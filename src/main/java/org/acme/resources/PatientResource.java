package org.acme.resources;

import org.acme.domain.Patient;
import org.acme.services.PatientService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @Inject
    PatientService service;

    @GET
    public List<Patient> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Patient getById(@PathParam("id") Long id) {
        return service.getById(id);
    }

    @POST
    public Response create(Patient patient) {
        Patient created = service.create(patient);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Patient update(@PathParam("id") Long id, Patient patient) {
        return service.update(id, patient);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
