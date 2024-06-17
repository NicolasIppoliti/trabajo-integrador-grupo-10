package org.acme.domain;


import org.acme.utils.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Patient {
    private Long id;
    @NotBlank(message = "El nombre del paciente no puede estar vacio.")
    private String firstName;
    @NotBlank(message = "El apellido del paciente no puede estar vacio.")
    private String lastName;
    @Email(message = "El email debe ser valido.")
    @NotBlank(message = "El email no puede estar vacio.")
    private String email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[-_@#$%^&+=]).*$",
             message = "La contraseña debe contener al menos un dígito, una letra minúscula, una letra mayúscula y un carácter especial (-_@#$%^&+=)")
    private String password;
    @NotBlank(message = "El teléfono no puede estar vacio.")
    @Size(min = 8, message = "El teléfono debe tener al menos 8 caracteres")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono debe contener solo dígitos")
    private String phone;
    @NotNull()
    private Role role;
}
