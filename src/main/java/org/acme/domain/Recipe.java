package org.acme.domain;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Recipe {
    private String description;
    private Long appointment_id;
    private LocalDate issueDate;
}
