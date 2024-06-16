package org.acme.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@NoArgsConstructor
@Entity
@Table(name = "appointments")
public class AppointmentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull(message = "Paciente no puede ser vacio.")
    //@JsonBackReference(value = "patient-appointments")
    private PatientEntity patient;

    @NotNull(message = "Día y fecha no puede ser vacios.")
    //@JsonProperty("date_hour")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    @Fetch(FetchMode.JOIN)
    @NotNull(message = "Especialista no puede ser vacio.")
    //@JsonBackReference(value = "doctor-appointments")
    private DoctorEntity doctor;

    @JsonIgnore
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeEntity> recipes;

    @NotBlank(message = "Razon del turno no puede ser vacio.")
    private String queryReason;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AppointmentEntity{" +
               "id=" + id +
               ", patient=" + (patient != null ? patient.getId() : null) + // Evita recursión
               ", dateHour=" + dateHour +
               ", doctor=" + (doctor != null ? doctor.getId() : null) + // Evita recursión
               ", queryReason=" + queryReason +
               '}';
    }

    public int hashCode() {
        return Objects.hash(id, dateHour, queryReason);
    }

    // Sobrescribir equals para evitar recursión
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentEntity that = (AppointmentEntity) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(dateHour, that.dateHour) &&
               Objects.equals(queryReason, that.queryReason);
    }
}
