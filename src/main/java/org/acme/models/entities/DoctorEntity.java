package org.acme.models.entities;

import java.util.HashSet;
import java.util.Set;

import org.acme.utils.Speciality;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacio")
    @JsonProperty("first_name")
    private String firstName;
    
    @NotBlank(message = "El apellido no puede estar vacio")
    @JsonProperty("last_name")
    private String lastName;
    
    @NotNull
    private int dni;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Speciality speciality;
    
    @JsonProperty("appointments")
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "doctor-appointments")
    private Set<AppointmentEntity> appointments = new HashSet<>();
    
    public void setId(Long id) {
        this.id = id;
    }
}
