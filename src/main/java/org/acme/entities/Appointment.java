package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Appointment extends PanacheEntity {

    @ManyToOne
    public Patient patient;

    public LocalDateTime dateHour;

    @ManyToOne
    public Doctor doctor;
    public String queryReason;

    // Getters y Setters
}