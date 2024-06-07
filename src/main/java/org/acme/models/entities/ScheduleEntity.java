package org.acme.models.entities;

import java.time.LocalTime;

import org.acme.utils.Day;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "schedules",
uniqueConstraints = @UniqueConstraint(columnNames = {"day", "entry_time", "departure_time"}))
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Day day;
    @NotNull
    @Column(name = "entry_time")
    private LocalTime entryTime;
    @NotNull
    @Column(name = "departure_time")
    private LocalTime departureTime;
}