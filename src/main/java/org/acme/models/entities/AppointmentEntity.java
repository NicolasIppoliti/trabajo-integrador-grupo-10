package org.acme.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @NotBlank(message = "Razon del turno no puede ser vacio.")
    private String queryReason;

    public void setId(Long id) {
        this.id = id;
    }
}
