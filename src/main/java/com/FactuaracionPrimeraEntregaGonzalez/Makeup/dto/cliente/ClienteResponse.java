package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.cliente;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class ClienteResponse {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDateTime creadoEn;
}
