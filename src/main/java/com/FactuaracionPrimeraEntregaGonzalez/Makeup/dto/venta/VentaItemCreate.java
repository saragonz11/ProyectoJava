package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.venta;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class VentaItemCreate {
    @NotNull private Long productoId;
    @NotNull @Min(1) private Integer cantidad;
}
