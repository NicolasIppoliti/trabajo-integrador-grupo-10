package org.acme.resources;

import org.acme.services.PatientService;
import org.acme.models.dto.PatientDTO;
import org.acme.mappers.PatientMapper;
import jakarta.inject.Inject;
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
    PatientService patientService;

    @GET
    public List<PatientDTO> listAll() {
        return patientService.listAll().stream()
            .map(PatientMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }

    @POST
    public Response add(PatientDTO patientDTO) {
        try {
            patientService.add(PatientMapper.INSTANCE.toEntity(patientDTO));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Error al crear el paciente: " + e.getMessage())
                           .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, PatientDTO patientDTO) {
        PatientDTO existing = PatientMapper.INSTANCE.toDTO(patientService.findById(id));
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Paciente no encontrado")
                           .build();
        }
        try {
            patientService.update(PatientMapper.INSTANCE.toEntity(patientDTO));
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Error al actualizar el paciente: " + e.getMessage())
                           .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        PatientDTO existing = PatientMapper.INSTANCE.toDTO(patientService.findById(id));
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Paciente no encontrado")
                           .build();
        }
        try {
            patientService.delete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Error al eliminar el paciente: " + e.getMessage())
                           .build();
        }
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id) {
        PatientDTO patient = PatientMapper.INSTANCE.toDTO(patientService.findById(id));
        if (patient == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Paciente no encontrado")
                           .build();
        }
        return Response.ok(patient).build();
    }
}
