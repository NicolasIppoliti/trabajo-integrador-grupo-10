package org.acme.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Branch {
    private Long id;
    @NotBlank(message = "El nombre de la sucursal no puede quedar en blanco.")
    private String name;
    @NotBlank(message = "La direccion de la sucursal no puede quedar en blanco.")
    private String address;
    @NotBlank(message = "La ciudad de la sucursal no puede quedar en blanco.")
    private String city;
}
