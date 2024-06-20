package org.acme.domain;

import java.util.Set;

import org.acme.models.entities.BranchEntity;
import org.acme.models.entities.ScheduleEntity;
import org.acme.utils.Speciality;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Speciality speciality;
    private Set<ScheduleEntity> schedules;
    private BranchEntity branch;
}
