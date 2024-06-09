package org.acme.models.entities;

import java.util.List;

import org.acme.utils.Branch;
import org.acme.utils.Hours;
import org.acme.utils.Speciality;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    
    @Enumerated(EnumType.STRING)
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private Hours workingHours;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "doctor-appointments")
    @JsonIgnore
    private List<AppointmentEntity> appointments;
    
    public void setId(Long id) {
        this.id = id;
    }
}
