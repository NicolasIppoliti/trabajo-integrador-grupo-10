package org.acme.models.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AppointmentDTO {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime dateHour;
    private String queryReason;
}
