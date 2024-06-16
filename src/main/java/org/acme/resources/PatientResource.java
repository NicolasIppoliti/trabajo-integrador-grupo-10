package org.acme.resources;

import org.acme.domain.Patient;
import org.acme.models.entities.PatientEntity;
import org.acme.repositories.PatientRepository;
import org.acme.services.PatientService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    @Inject
    PatientService service;

    @Inject
    PatientRepository repository;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response getAll() {
        try {
            List<Patient> patients = service.getAll();
            return Response.ok(patients).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener los pacientes")
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            Patient patient = service.getById(id);
            if (patient == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Paciente no encontrado").build();
            }
            return Response.ok(patient).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener el paciente")
                    .build();
        }
    }

    @SuppressWarnings("resource") // Suprimido por falso positivo
    @POST
    public Response create(@Valid Patient patient) {
        try {
            PatientEntity existingPatient = repository.findByEmail(patient.getEmail());

            if (existingPatient != null) {
                throw new WebApplicationException(
                        Response.status(409).entity("Usuario ya creado con este email.").build());
            }
            Patient createdPatient = service.create(patient);
            return Response.status(Response.Status.CREATED).entity(createdPatient).build();
        } catch (ConstraintViolationException e) {
            String violations = e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Patient patient) {
        try {
            Patient updatedPatient = service.update(id, patient);
            if (updatedPatient == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Paciente no encontrado").build();
            }
            return Response.ok(updatedPatient).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar el paciente")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (service.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Paciente no encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el paciente")
                    .build();
        }
    }
}
