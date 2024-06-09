package org.acme.services;

import org.acme.repositories.AppointmentRepository;
import org.acme.models.entities.AppointmentEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AppointmentService {

    @Inject
    AppointmentRepository appointmentRepository;

    public List<AppointmentEntity> listAll() {
        return appointmentRepository.listAll();
    }

    @Transactional
    public void add(AppointmentEntity appointment) {
        appointmentRepository.persist(appointment);
    }

    @Transactional
    public void update(AppointmentEntity appointment) {
        appointmentRepository.getEntityManager().merge(appointment);
    }

    @Transactional
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    public AppointmentEntity findById(Long id) {
        return appointmentRepository.findById(id);
    }
}
