package org.acme.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Appointment {
    private Long id;
    private Long patient_id;
    private LocalDateTime dateHour;
    private Long doctor_id;
    private String queryReason;
}
