package org.acme.resources;

import org.acme.entities.Branch;
import org.acme.repositories.BranchRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/sucursales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BranchResource {

    @Inject
    BranchRepository branchRepository;

    @GET
    public Response getBranches() {
        List<Branch> branches = branchRepository.listAll();
        return Response.ok(branches).build();
    }

    @GET
    @Path("/{id}")
    public Response getBranchById(@PathParam("id") Long id) {
        Branch branch = branchRepository.findById(id);
        return Response.ok(branch).build();
    }


    @POST
    @Transactional
    public Response createBranch(Branch branch) {
        branch.setId(null);
        branchRepository.persist(branch);
        return Response.status(Response.Status.CREATED).entity(branch).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response updateBranch(@PathParam("id") Long id, Branch updatedBranch) {
        Branch branch = branchRepository.findById(id);
        if (branch == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Branch not found with id " + id)
                           .build();
        }
        branch.setName(updatedBranch.getName());
        branch.setAddress(updatedBranch.getAddress());
        branch.setCity(updatedBranch.getCity());
        updatedBranch.setId(null);
        branchRepository.persist(updatedBranch);

        return Response.ok(updatedBranch).build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteBranch(@PathParam("id") Long id) {
        Branch branch = branchRepository.findById(id);
        if (branch == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Branch not found with id " + id)
                           .build();
        }
        branchRepository.delete(branch);
        return Response.noContent().build();
    }
}
