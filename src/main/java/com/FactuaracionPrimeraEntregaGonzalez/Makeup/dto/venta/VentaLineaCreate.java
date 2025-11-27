package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.venta;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class VentaLineaCreate {
    @NotNull @Min(1)
    private Integer cantidad;

    @NotNull @Valid
    private VentaProductoRef producto;
}