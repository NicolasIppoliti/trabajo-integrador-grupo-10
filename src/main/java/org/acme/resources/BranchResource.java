package org.acme.resources;

import org.acme.domain.Branch;
import org.acme.services.BranchService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/sucursales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BranchResource {

    @Inject
    BranchService service;

    @GET
    public List<Branch> getAll() {
        return service.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Branch branch = service.getById(id);
        if (branch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(branch).build();
    }

    @POST
    public Response create(Branch branch) {
    	try {
            Branch createdBranch = service.create(branch);
            return Response.status(Response.Status.CREATED).entity(createdBranch).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Branch branch) {
        Branch updatedBranch = service.update(id, branch);
        if (updatedBranch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(updatedBranch).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (service.delete(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
