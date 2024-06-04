package org.acme.repositories;

import org.acme.entities.Appointment;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AppointmentRepository implements PanacheRepository<Appointment>{

}
