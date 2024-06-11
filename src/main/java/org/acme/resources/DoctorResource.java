package org.acme.resources;

import org.acme.domain.DoctorRequestDTO;
import org.acme.domain.DoctorResponseDTO;
import org.acme.models.entities.DoctorEntity;
import org.acme.services.DoctorService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/especialistas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

    @Inject
    DoctorService service;

    @GET
    public Response getAll() {      //LISTO OK!
        try {
            List<DoctorResponseDTO> doctors = service.getAll();
            return Response.ok(doctors).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener los especialistas").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {         //LISTO OK!
        try {
            DoctorResponseDTO doctor = service.getById(id);
            if (doctor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Especialista no encontrado").build();
            }
            return Response.ok(doctor).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener el especialista").build();
        }
    }

    @POST
    public Response create(DoctorRequestDTO doctor) {       //LISTO OK!!
        try {
            DoctorEntity createdDoctor = service.create(doctor);
            return Response.status(Response.Status.CREATED).entity(createdDoctor).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al crear el especialista: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, DoctorRequestDTO doctor) {         //LISTO OK!
        System.out.println("llame al update hasta el service....*********************************************************************");
        try {
            DoctorEntity updatedDoctor = service.update(id, doctor);
            if (updatedDoctor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Especialista no encontrado").build();
            }
            return Response.ok(updatedDoctor).entity(updatedDoctor).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar el especialista").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {          //LISTO OK!
        try {
            if (service.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Especialista no encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el especialista").build();
        }
    }
}
