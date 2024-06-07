package org.acme.models.entities;

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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class PatientEntity {
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

    @JsonProperty("appointments")
    @OneToMany(mappedBy = "patient")
    @JsonManagedReference(value = "patient-appointments")
    private List<AppointmentEntity> appointments;
}
