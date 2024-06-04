package org.acme.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Patient extends PanacheEntity {

    public String firstName;
    public String lastName;
    public String email;
    public String phone;

    @OneToMany(mappedBy = "paciente")
    public List<Appointment> appointments;

    // Getters y Setters
}