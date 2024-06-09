package org.acme.models.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RecipeDTO {
    private Long id;
    private String description;
    private Long appointmentId;
    private LocalDate issueDate;
}
