package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Doctor extends PanacheEntity {

    public String name;
    public String speciality;
    public String schedules;
    public String location;

    // Getters y Setters
}
