package org.acme.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Appointment {
    private Long id;
    private Patient patient;
    private LocalDateTime dateHour;
    private DoctorResponseDTO doctor;
    private String queryReason;
}
