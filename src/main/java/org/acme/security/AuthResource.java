package org.acme.security;

import org.acme.models.entities.PatientEntity;
import org.acme.repositories.PatientRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)

@Produces(MediaType.APPLICATION_JSON)

public class AuthResource {

  @Inject
  TokenService service;

  @Inject
  PatientRepository patientRepository;

  @Transactional
  @GET
  @Path("/login")
  public Response login(@QueryParam("email")String email, @QueryParam("password") String password) {
    System.out.println("Aqui*************" + patientRepository.findByEmail(email));
    PatientEntity existingPatient = patientRepository.findByEmail(email);

    if(existingPatient == null || !existingPatient.getPassword().equals(password)) {
      throw new WebApplicationException(Response.status(404).entity("No user found or password is incorrect").build());

    }
    String token = service.generatePatientToken(existingPatient);
    return Response.ok(token).build();
  }

}

