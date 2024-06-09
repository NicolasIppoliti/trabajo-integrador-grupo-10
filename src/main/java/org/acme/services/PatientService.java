package org.acme.services;

import org.acme.models.entities.PatientEntity;
import org.acme.repositories.PatientRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PatientService {

    @Inject
    PatientRepository patientRepository;

    public List<PatientEntity> listAll() {
        return patientRepository.listAll();
    }

    public PatientEntity findById(Long id) {
        return patientRepository.findById(id);
    }

    @Transactional
    public void add(PatientEntity patientEntity) {
        patientRepository.persist(patientEntity);
    }

//	  No es necesario para la entrega
    
//    @Transactional
//    public void update(PatientEntity patientEntity) {
//        patientRepository.getEntityManager().merge(patientEntity);
//    }
//
//    @Transactional
//    public void delete(Long id) {
//        patientRepository.deleteById(id);
//    }
}
