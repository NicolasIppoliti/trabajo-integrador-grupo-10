package org.acme.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalTime;

import org.acme.utils.Day;

import io.smallrye.common.constraint.NotNull;

@Data
@AllArgsConstructor
public class Schedule {
    private Long id;
    @NotNull
    private Day day;
    @NotNull
    private LocalTime entryTime;
    @NotNull
    private LocalTime departureTime;
}
