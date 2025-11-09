package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.producto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductoResponse {
    private Long id; private String sku; private String nombre;
    private String descripcion; private BigDecimal precio;
    private Integer stock; private Boolean activo; private LocalDateTime creadoEn;
}