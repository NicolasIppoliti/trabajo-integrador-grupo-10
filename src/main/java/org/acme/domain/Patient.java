package org.acme.domain;

import lombok.Data;
import java.util.List;

@Data
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
   // private List<Appointment> appointments;
}
