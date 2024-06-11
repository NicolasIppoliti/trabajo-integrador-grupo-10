package org.acme.security;

import org.acme.models.entities.PatientEntity;
import org.acme.repositories.PatientRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class AuthResource {

    @Path("/users")

@Consumes(MediaType.APPLICATION_JSON)

@Produces(MediaType.APPLICATION_JSON)

public class UserResource {

  @Inject
  TokenService service;

  @Inject
  PatientRepository patientRepository;


  @GET

  @Path("/login")

  public String login(@QueryParam("email")String email, @QueryParam("password") String password) {
    PatientEntity existingPatient = patientRepository.findByEmail(email);

    if(existingPatient == null || !existingPatient.getPassword().equals(password)) {
      throw new WebApplicationException(Response.status(404).entity("No user found or password is incorrect").build());

    }

    return service.generatePatientToken(existingPatient.getEmail(), existingPatient.getPassword());

  }

}
    
}
