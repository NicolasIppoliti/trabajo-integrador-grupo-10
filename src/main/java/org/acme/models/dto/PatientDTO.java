package org.acme.models.dto;

import lombok.Data;
import java.util.List;

@Data
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private List<Long> appointmentIds;
}
