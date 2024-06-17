package org.acme.models.entities;

import java.util.HashSet;
import java.util.Set;

import org.acme.utils.Speciality;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "doctors")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "doctor_schedule",
        joinColumns = @JoinColumn(name = "doctor_id"),
        inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    private Set<ScheduleEntity> schedules = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    @NotNull
    private BranchEntity branch;
    
    @JsonIgnore
    @JsonProperty("appointments")
    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER)
    @JsonManagedReference(value = "doctor-appointments")
    private Set<AppointmentEntity> appointments = new HashSet<>();
    
    public void setId(Long id) {
        this.id = id;
    }
    
}
