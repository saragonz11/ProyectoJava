package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.venta;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class VentaCreateRequest {
    @NotNull private Long clienteId;
    @NotEmpty private List<VentaItemCreate> items;
}
