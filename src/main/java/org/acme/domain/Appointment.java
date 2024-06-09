package org.acme.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Appointment {
    private Long id;
    private Long patient_id;
    private LocalDateTime dateHour;
    private Long doctor_id;
    private String queryReason;
}
