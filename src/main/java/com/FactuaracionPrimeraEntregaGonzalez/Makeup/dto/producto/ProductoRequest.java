package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.producto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductoRequest {
    @NotBlank @Size(max=40) private String sku;
    @NotBlank @Size(max=160) private String nombre;
    @Size(max=400) private String descripcion;
    @NotNull @DecimalMin("0.00") private BigDecimal precio;
    @NotNull @Min(0) private Integer stock;
    private Boolean activo = true;
}
