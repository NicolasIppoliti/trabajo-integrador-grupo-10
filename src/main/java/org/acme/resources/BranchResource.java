package org.acme.resources;

import org.acme.domain.Branch;
import org.acme.services.BranchService;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/sucursales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BranchResource {

    @Inject
    BranchService service;

    @GET
    public Response getAll() {
        try {
            List<Branch> branches = service.getAll();
            return Response.ok(branches).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener las sucursales").build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            Branch branch = service.getById(id);
            if (branch == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Sucursal no encontrada").build();
            }
            return Response.ok(branch).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener la sucursal").build();
        }
    }

    @POST
    public Response create(@Valid Branch branch) {
        try {
            Branch createdBranch = service.create(branch);
            return Response.status(Response.Status.CREATED).entity(createdBranch).build();
        } catch (ConstraintViolationException e) {
            String violations = e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id,@Valid Branch branch) {
        try {
            Branch updatedBranch = service.update(id, branch);
            if (updatedBranch == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Sucursal no encontrada").build();
            }
            return Response.ok(updatedBranch).build();
        } catch (ConstraintViolationException e) {
            String violations = e.getConstraintViolations().stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining(", "));
            return Response.status(Response.Status.BAD_REQUEST).entity(violations).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            if (service.delete(id)) {
                return Response.status(Response.Status.NO_CONTENT).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Sucursal no encontrada").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar la sucursal").build();
        }
    }
}
