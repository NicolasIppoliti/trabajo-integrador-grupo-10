package org.acme.services;

import org.acme.models.entities.DoctorEntity;
import org.acme.repositories.DoctorRepository;

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

    public DoctorEntity findById(Long id) {
        return doctorRepository.findById(id);
    }

    @Transactional
    public void add(DoctorEntity doctorEntity) {
        doctorRepository.persist(doctorEntity);
    }
    
// 	  No es necesario para la entrega
    
//    @Transactional
//    public void update(DoctorEntity doctorEntity) {
//        doctorRepository.getEntityManager().merge(doctorEntity);
//    }
//
//    @Transactional
//    public void delete(Long id) {
//        doctorRepository.deleteById(id);
//    }
}
