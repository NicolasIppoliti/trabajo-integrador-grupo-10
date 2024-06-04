package org.acme.entities;

import java.util.HashSet;
import java.util.Set;

import org.acme.utils.Speciality;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String name;
    @NotBlank(message = "El apellido no puede estar vacio")
    private String surname;
    @NotNull
    private int dni;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    @ManyToMany
    @JoinTable(
        name = "doctor_schedule",
        joinColumns = @JoinColumn(name = "doctor_id"),
        inverseJoinColumns = @JoinColumn(name = "schedule_id")
    )
    private Set<Schedule> schedule = new HashSet<>();
    
}
