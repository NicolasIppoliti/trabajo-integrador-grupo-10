package org.acme.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalTime;

import org.acme.utils.Day;

@Data
@AllArgsConstructor
public class Schedule {
    private Long id;
    private Day day;
    private LocalTime entryTime;
    private LocalTime departureTime;
}
