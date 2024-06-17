package org.acme.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Recipe {
    private String description;
    private Long appointment_id;
    private LocalDate issueDate;
}
