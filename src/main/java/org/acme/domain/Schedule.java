package org.acme.domain;

import lombok.Data;
import java.time.LocalTime;

import org.acme.utils.Day;

@Data
public class Schedule {
    private Long id;
    private Day day;
    private LocalTime entry_time;
    private LocalTime departure_time;
}
