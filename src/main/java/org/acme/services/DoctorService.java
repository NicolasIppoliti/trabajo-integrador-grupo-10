package org.acme.services;

import java.util.List;
import java.util.stream.Collectors;

import org.acme.domain.DoctorRequestDTO;
import org.acme.domain.DoctorResponseDTO;
import org.acme.mappers.DoctorRequestMapper;
import org.acme.mappers.DoctorResponseMapper;
import org.acme.models.entities.BranchEntity;
import org.acme.models.entities.DoctorEntity;
import org.acme.repositories.BranchRepository;
import org.acme.repositories.DoctorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DoctorService {

    @Inject
    DoctorRepository repository;

    @Inject
    BranchRepository branchRepository;

    @Inject
    DoctorRequestMapper requestMapper;

    @Inject
    DoctorResponseMapper responseMapper;

    @Inject
    EntityManager entityManager;

    public List<DoctorResponseDTO> getAll(){
        return repository.listAll().stream().map(responseMapper::toDomain).collect(Collectors.toList());
    }

    public DoctorResponseDTO getById(Long id) {
        return responseMapper.toDomain(repository.findById(id));
    }

    @Transactional
    public DoctorEntity create(DoctorRequestDTO doctor) {
        BranchEntity branch = branchRepository.findById(doctor.getBranch_id());
        DoctorEntity entity = new DoctorEntity();

        entity.setFirstName(doctor.getFirstName());
        entity.setLastName(doctor.getLastName());
        entity.setDni(doctor.getDni());
        entity.setSpeciality(doctor.getSpeciality());
        entity.setBranch(branch);
        repository.persist(entity);

        return entity;  //Seria util no devolver la entidad entera
    }

    @Transactional
    public DoctorEntity update(Long id, DoctorRequestDTO doctor) {
        BranchEntity branch = branchRepository.findById(doctor.getBranch_id());
        DoctorEntity existingEntity = repository.findById(id);
        if (existingEntity != null) {
            var updatedEntity = requestMapper.toEntity(doctor);     //TODO!!! DOCTOR UPDATE Y TODOS LOS DEMAS METODOS: MAÑANA VALIDACIONES PARA CREAR TURNOS
            updatedEntity.setId(id);
            updatedEntity.setFirstName(doctor.getFirstName());
            updatedEntity.setLastName(doctor.getLastName());
            updatedEntity.setDni(doctor.getDni());
            updatedEntity.setSpeciality(doctor.getSpeciality());
            updatedEntity.setBranch(branch);
            System.out.println("Casi persisto...");
            entityManager.merge(updatedEntity);
            System.out.println("PErsistido???");
            return updatedEntity;
        }
        return null;
    }

    @Transactional
    public boolean delete(Long id) {
        var existingEntity = repository.findById(id);
        if (existingEntity != null) {
            repository.delete(existingEntity);
            return true;
        }
        return false;
    }
}
