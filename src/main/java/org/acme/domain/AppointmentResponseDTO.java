package org.acme.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private PatientResponseDTO patient;
    private LocalDateTime dateHour;
    private DoctorResponseDTO doctor;
    private String queryReason;
}
