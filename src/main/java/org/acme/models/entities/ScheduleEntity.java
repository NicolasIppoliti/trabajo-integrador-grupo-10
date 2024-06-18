package org.acme.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import org.acme.utils.Day;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedules", uniqueConstraints = @UniqueConstraint(columnNames = {"day", "entry_time", "departure_time"}))
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

    public void setId(Long id) {
        this.id = id;
    }
}
