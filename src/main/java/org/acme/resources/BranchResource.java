package org.acme.resources;

import org.acme.entities.Branch;
import org.acme.repositories.BranchRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/sucursales")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BranchResource {

    @Inject
    BranchRepository branchRepository;

    @GET
    public List<Branch> getBranches() {
        return branchRepository.listAll();
    }

    @POST
    public Branch createBranch(Branch branch) {
        branchRepository.persist(branch);
        return branch;
    }

    @PUT
    @Path("/{id}")
    public Branch updateBranch(@PathParam("id") Long id, Branch branch) {
        Branch entity = branchRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Branch not found", 404);
        }
        entity.setName(branch.getName());
        entity.setAddress(branch.getAddress());
        entity.setCity(branch.getCity());
        branchRepository.persist(entity);
        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void deleteBranch(@PathParam("id") Long id) {
        Branch entity = branchRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Branch not found", 404);
        }
        branchRepository.delete(entity);
    }
}
