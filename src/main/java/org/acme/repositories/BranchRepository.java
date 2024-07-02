package org.acme.repositories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.acme.models.entities.BranchEntity;
import org.acme.models.entities.DoctorEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class BranchRepository implements PanacheRepositoryBase<BranchEntity, Long>{

    @Inject
    EntityManager entityManager;
    
    public Set<BranchEntity> findByDoctors(List<DoctorEntity> doctors) {
        Set<BranchEntity> branches = new HashSet<>();

        for (DoctorEntity doctor : doctors) {
            List<BranchEntity> branchesWithDoctor = entityManager.createQuery(
                    "SELECT DISTINCT d.branch FROM DoctorEntity d WHERE d.speciality = :speciality",
                    BranchEntity.class)
                    .setParameter("speciality", doctor.getSpeciality())
                    .getResultList();
            branches.addAll(branchesWithDoctor);
        }

        return branches;
    }
}
