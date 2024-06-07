package org.acme.domain;

import lombok.Data;
import java.util.Set;

import org.acme.utils.Speciality;

@Data
public class Doctor {
    private Long id;
    private String firstName;
    private String lastName;
    private int dni;
    private Speciality speciality;
    private Set<Schedule> schedules;
    private Branch branch;
    private Set<Appointment> appointments;
}
