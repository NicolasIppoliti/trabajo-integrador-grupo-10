package org.acme.services;

import org.acme.repositories.PatientRepository;
import org.acme.models.entities.PatientEntity;
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

    @Transactional
    public void add(PatientEntity patient) {
        patientRepository.persist(patient);
    }

    @Transactional
    public void update(PatientEntity patient) {
        patientRepository.getEntityManager().merge(patient);
    }

    @Transactional
    public void delete(Long id) {
        patientRepository.deleteById(id);
    }

    public PatientEntity findById(Long id) {
        return patientRepository.findById(id);
    }
}
