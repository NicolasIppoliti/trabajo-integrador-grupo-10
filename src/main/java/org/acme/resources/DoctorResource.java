package org.acme.resources;

import org.acme.services.DoctorService;
import org.acme.models.dto.DoctorDTO;
import org.acme.mappers.DoctorMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/especialistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

    @Inject
    DoctorService doctorService;

    @GET
    public List<DoctorDTO> listAll() {
        return doctorService.listAll().stream()
            .map(DoctorMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }

    @POST
    public Response add(DoctorDTO doctorDTO) {
        try {
            doctorService.add(DoctorMapper.INSTANCE.toEntity(doctorDTO));
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Error al crear el especialista: " + e.getMessage())
                           .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, DoctorDTO doctorDTO) {
        DoctorDTO existing = DoctorMapper.INSTANCE.toDTO(doctorService.findById(id));
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Especialista no encontrado")
                           .build();
        }
        try {
            doctorService.update(DoctorMapper.INSTANCE.toEntity(doctorDTO));
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Error al actualizar el especialista: " + e.getMessage())
                           .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        DoctorDTO existing = DoctorMapper.INSTANCE.toDTO(doctorService.findById(id));
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Especialista no encontrado")
                           .build();
        }
        try {
            doctorService.delete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Error al eliminar el especialista: " + e.getMessage())
                           .build();
        }
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id) {
        DoctorDTO doctor = DoctorMapper.INSTANCE.toDTO(doctorService.findById(id));
        if (doctor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Especialista no encontrado")
                           .build();
        }
        return Response.ok(doctor).build();
    }
}
