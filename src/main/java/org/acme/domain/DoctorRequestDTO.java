package org.acme.domain;

import lombok.Data;

import org.acme.utils.Speciality;

@Data
public class DoctorRequestDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int dni;
    private Speciality speciality;
    private Long branch_id;
}
