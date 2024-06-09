package org.acme.domain;

import java.util.Set;

import org.acme.utils.Speciality;

import lombok.Data;

@Data
public class DoctorResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private int dni;
    private Speciality speciality;
    private Set<Schedule> schedules;
    private Branch branch;
    private Set<Appointment> appointments;
}