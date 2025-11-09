package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.cliente;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ClienteRequest {
    @NotBlank @Size(max=120) private String nombre;
    @NotBlank @Email @Size(max=150) private String email;
    @Size(max=30) private String telefono;
    @Size(max=200) private String direccion;
}
