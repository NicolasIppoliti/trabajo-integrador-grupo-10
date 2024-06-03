package org.acme.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El nombre del centro medico no puede estar vacío")
    private String name;
    @NotBlank(message = "la direccion del centro medico no puede estar vacío")
    private String address;
    @NotBlank(message = "La ciudad del centro medico no puede estar vacía")
    private String city;
    
    public Branch(@NotBlank(message = "El nombre del centro medico no puede estar vacío") String name,
            @NotBlank(message = "la direccion del centro medico no puede estar vacío") String address,
            @NotBlank(message = "La ciudad del centro medico no puede estar vacía") String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }
    
}
