package org.acme.security;

import org.acme.mappers.PatientMapper;
import org.acme.models.entities.PatientEntity;
import org.acme.repositories.PatientRepository;
import org.acme.utils.Role;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
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

    @Path("/users")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

  @Inject
  TokenService service;

  @Inject
  PatientRepository patientRepository;
  
  @Inject
  PatientMapper mapper;

  @SuppressWarnings("resource") // Falso positivo, por eso el SuppressWarnings
  @Transactional
  @GET
  @Path("/login")
  public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
    System.out.println("Aqui*************" + patientRepository.findByEmail(email));
    PatientEntity existingPatient = patientRepository.findByEmail(email);

    if (existingPatient == null || !existingPatient.getPassword().equals(password)) {
      throw new WebApplicationException(Response.status(404).entity("No user found or password is incorrect").build());
    }

    String token = service.generatePatientToken(existingPatient);
    return Response.ok(token).build();
  }
  
  @SuppressWarnings("resource") // Falso positivo, por eso el SuppressWarnings
  @Transactional
  @POST
  @Path("/register")
  public Response register(@QueryParam("email") String email, @QueryParam("password") String password, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("phone") String phone) {
    PatientEntity existingPatient = patientRepository.findByEmail(email);

    if (existingPatient != null) {
      throw new WebApplicationException(Response.status(404).entity("Usuario ya creado con este email.").build());
    }

    PatientEntity newPatient = new PatientEntity();
    newPatient.setEmail(email);
    newPatient.setPassword(password);
    newPatient.setFirstName(firstName);
    newPatient.setLastName(lastName);
    newPatient.setPhone(phone);
    newPatient.setRole(Role.PATIENT);

    patientRepository.persist(newPatient);

    String token = service.generatePatientToken(newPatient);

    JsonObject jsonResponse = Json.createObjectBuilder()
                                  .add("token", token)
                                  .build();

    return Response.ok(jsonResponse).build();
  }
}