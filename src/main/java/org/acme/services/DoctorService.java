package org.acme.services;

import org.acme.repositories.DoctorRepository;
import org.acme.models.entities.DoctorEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class DoctorService {

    @Inject
    DoctorRepository doctorRepository;

    public List<DoctorEntity> listAll() {
        return doctorRepository.listAll();
    }

    @Transactional
    public void add(DoctorEntity doctor) {
        doctorRepository.persist(doctor);
    }

    @Transactional
    public void update(DoctorEntity doctor) {
        doctorRepository.getEntityManager().merge(doctor);
    }

    @Transactional
    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    public DoctorEntity findById(Long id) {
        return doctorRepository.findById(id);
    }
}
