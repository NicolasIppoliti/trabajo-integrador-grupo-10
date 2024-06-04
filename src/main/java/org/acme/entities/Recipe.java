package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Recipe extends PanacheEntity {

    public String description;

    @ManyToOne
    public Appointment appointment;

    public LocalDate issueDate;

    // Getters y Setters
}
