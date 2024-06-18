package org.acme.security;

import java.util.stream.Collectors;

import org.acme.domain.Patient;
import org.acme.mappers.PatientMapper;
import org.acme.models.entities.PatientEntity;
import org.acme.repositories.PatientRepository;
import org.acme.services.PatientService;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/auth")
public class AuthResource {

  @Inject
  TokenService service;

  @Inject
  PatientRepository patientRepository;

  @Inject
  PatientService pService;
  
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
  @POST
  @Path("/register")
  public Response register(@Valid Patient patient) {
    try {
      PatientEntity existingPatient = patientRepository.findByEmail(patient.getEmail());

      if (existingPatient != null) {
        throw new WebApplicationException(Response.status(409).entity("Usuario ya creado con este email.").build());
      }
      String token = pService.RegisterAndGetToken(patient);

      JsonObject jsonResponse = Json.createObjectBuilder()
          .add("token", token)
          .build();

      // Devuelvo respuesta 201 y el string del token creado!
      return Response.ok(jsonResponse).build();
    } catch (ConstraintViolationException e) {
      String violations = e.getConstraintViolations().stream()
          .map(violation -> violation.getMessage())
          .collect(Collectors.joining(", "));
      return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
    }
  }

}