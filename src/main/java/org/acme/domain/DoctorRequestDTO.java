package org.acme.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.acme.utils.Speciality;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class DoctorRequestDTO {
    @NotBlank(message = "El doctor debe tener nombre de pila")
    private String firstName;
    @NotBlank(message = "El doctor debe tener apellido")
    private String lastName;
    @NotNull
    private int dni;
    @NotNull
    private Speciality speciality;
    @NotNull
    private Long branch_id;
}
