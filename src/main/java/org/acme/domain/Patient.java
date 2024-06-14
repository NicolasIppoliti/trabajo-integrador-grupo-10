package org.acme.domain;

import lombok.Data;
import java.util.List;

import org.acme.utils.Role;

@Data
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Role role;
   // private List<Appointment> appointments;
}
