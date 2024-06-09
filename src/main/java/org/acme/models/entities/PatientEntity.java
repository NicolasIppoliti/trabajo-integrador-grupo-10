package org.acme.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Apellido no puede ser vacio.")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email no puede ser vacio.")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Contraseña no puede ser vacio.")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Telefono no puede ser vacio.")
    @Column(name = "phone", unique = true)
    private String phone;

    @JsonProperty("appointments")
    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "patient-appointments")
    private List<AppointmentEntity> appointments = new ArrayList<>();
    
    public void setId(Long id) {
        this.id = id;
    }
}
