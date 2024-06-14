package org.acme.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import org.acme.utils.Day;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@Entity
@Table(name = "schedules", uniqueConstraints = { 
        @UniqueConstraint(columnNames = {"day", "entry_time", "departure_time"}),
        @UniqueConstraint(columnNames = {"doctor_id", "day"})
    })
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Day cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    @JsonProperty("day")
    private Day day;

    @NotNull(message = "Entry time cannot be null")
    @Column(name = "entry_time")
    @JsonProperty("entryTime")
    private LocalTime entryTime;

    @NotNull(message = "Departure time cannot be null")
    @Column(name = "departure_time")
    @JsonProperty("departureTime")
    private LocalTime departureTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctorEntity;

    public void setId(Long id) {
        this.id = id;
    }
}
