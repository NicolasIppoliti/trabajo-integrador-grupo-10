package org.acme.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre no puede ser vacio.")
    private String firstName;

    @NotBlank(message = "Apellido no puede ser vacio.")
    private String lastName;

    @NotBlank(message = "Email no puede ser vacio.")
    private String email;

    @NotBlank(message = "Contraseña no puede ser vacio.")
    private String password;

    @NotBlank(message = "Telefono no puede ser vacio.")
    private String phone;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
}
