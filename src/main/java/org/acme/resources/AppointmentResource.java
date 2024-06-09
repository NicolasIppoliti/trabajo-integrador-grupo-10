package org.acme.resources;

import org.acme.services.AppointmentService;
import org.acme.models.dto.AppointmentDTO;
import org.acme.models.dto.ResponseMessage;
import org.acme.models.entities.AppointmentEntity;
import org.acme.mappers.AppointmentMapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/turnos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    @Inject
    AppointmentService appointmentService;

    @GET
    public List<AppointmentDTO> listAll() {
        return appointmentService.listAll().stream()
            .map(AppointmentMapper.INSTANCE::toDTO)
            .collect(Collectors.toList());
    }
    
    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") Long id) {
        AppointmentEntity appointment = appointmentService.findById(id);
        if (appointment == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage("Turno no encontrado")).build();
        }
        return Response.ok(AppointmentMapper.INSTANCE.toDTO(appointment)).build();
    }

    @POST
    public Response add(AppointmentDTO appointmentDTO) {
        try {
            AppointmentEntity appointmentEntity = AppointmentMapper.INSTANCE.toEntity(appointmentDTO);
            appointmentService.add(appointmentEntity, appointmentDTO.getPatientId(), appointmentDTO.getDoctorId());
            return Response.status(Response.Status.CREATED)
                           .entity(new ResponseMessage("Turno creado exitosamente"))
                           .build();
        } catch (Exception e) {
        	e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new ResponseMessage("Error al crear el turno: " + e.getMessage()))
                           .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") Long id, AppointmentDTO appointmentDTO) {
        try {
            appointmentService.update(id, appointmentDTO);
            return Response.ok(new ResponseMessage("Turno actualizado exitosamente")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new ResponseMessage("Error al actualizar el turno: " + e.getMessage()))
                           .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            System.out.println("Request to delete appointment with ID: " + id);
            appointmentService.delete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity(new ResponseMessage("Error al eliminar el turno: " + e.getMessage()))
                           .build();
        }
    }
}
