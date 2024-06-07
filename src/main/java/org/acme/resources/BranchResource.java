package org.acme.resources;

import org.acme.models.entities.Branch;
import org.acme.repositories.BranchRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/branches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BranchResource {

    @Inject
    BranchRepository branchRepository;

    @GET
    public List<Branch> getAll() {
        return branchRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Branch branch = branchRepository.findById(id);
        if (branch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(branch).build();
    }

    @POST
    @Transactional
    public Response create(Branch branch) {
    	branchRepository.persist(branch);
        return Response.status(Response.Status.CREATED).entity(branch).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Branch branch) {
        Branch existingBranch = branchRepository.findById(id);
        if (existingBranch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingBranch.setName(branch.getName());
        existingBranch.setAddress(branch.getAddress());
        existingBranch.setCity(branch.getCity());
        return Response.ok(existingBranch).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Branch existingBranch = branchRepository.findById(id);
        if (existingBranch == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        branchRepository.delete(existingBranch);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
