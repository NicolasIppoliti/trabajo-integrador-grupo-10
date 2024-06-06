package org.acme.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del centro medico no puede estar vacío")
    private String name;
    
    @NotBlank(message = "la direccion del centro medico no puede estar vacío")
    private String address;
    
    @NotBlank(message = "La ciudad del centro medico no puede estar vacía")
    private String city;

    @OneToMany
    private Set<Doctor> doctors = new HashSet<>();
}
