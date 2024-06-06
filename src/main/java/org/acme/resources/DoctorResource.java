package org.acme.resources;

import java.util.List;

import org.acme.models.entities.Branch;
import org.acme.models.entities.Doctor;
import org.acme.repositories.BranchRepository;
import org.acme.repositories.DoctorRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/doctors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoctorResource {

	@Inject
	DoctorRepository doctorRepository;

    BranchRepository branchRepository;
	
    @GET
    @Transactional
    public Response getDoctors() {
        List<Doctor> doctors = doctorRepository.listAll();
        return Response.ok(doctors).build();
    }

    @GET
    @Transactional
    @Path("/{id}")
    public Response getDoctorById(@PathParam("id") Long id) {
        Doctor doctor = doctorRepository.findById(id);
        return Response.ok(doctor).build();
    }


    @POST
    @Transactional
    public Response createDoctor(Doctor doctor) {
        doctor.setId(null);
        doctorRepository.persist(doctor);
        return Response.status(Response.Status.CREATED).entity(doctor).build();
    }

    // @PUT
    // @Transactional
    // @Path("/{id}")
    // public Response updateDoctor(@PathParam("id") Long id, Doctor updatedDoctor) {
    //     Doctor doctor = doctorRepository.findById(id);
    //     Branch branch = branchRepository.findById(id;)
    //     if (doctor == null) {
    //         return Response.status(Response.Status.NOT_FOUND)
    //                        .entity("Doctor not found with id " + id)
    //                        .build();
    //     }
    //     doctor.setFirstName(updatedDoctor.getFirstName());
    //     doctor.setLastName(updatedDoctor.getLastName());
    //     doctor.setDni(updatedDoctor.getDni());
    //     doctor.setSpeciality(updatedDoctor.getSpeciality());
    //     doctor.setDni(updatedDoctor.getDni());
    //     updatedDoctor.setBranch(null);
    //     doctorRepository.persist(doctor);

    //     return Response.ok(doctor).build();
    // }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteDoctor(@PathParam("id") Long id) {
        Doctor doctor = doctorRepository.findById(id);
        if (doctor == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Doctor not found with id " + id)
                           .build();
        }
        doctorRepository.delete(doctor);
        return Response.noContent().build();
    }
}
