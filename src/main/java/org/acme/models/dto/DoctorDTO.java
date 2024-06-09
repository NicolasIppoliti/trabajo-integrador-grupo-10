package org.acme.models.dto;

import org.acme.utils.Branch;
import org.acme.utils.Hours;
import org.acme.utils.Speciality;
import lombok.Data;
import java.util.List;

@Data
public class DoctorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int dni;
    private Speciality speciality;
    private Branch branch;
    private Hours workingHours;
    private List<Long> appointmentIds;
}
