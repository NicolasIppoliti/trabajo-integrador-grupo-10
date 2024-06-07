package org.acme.domain;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Recipe {
    private Long id;
    private String description;
    private Appointment appointment;
    private LocalDate issueDate;
}
