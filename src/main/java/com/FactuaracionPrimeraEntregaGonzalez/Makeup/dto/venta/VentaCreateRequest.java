package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.venta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
@Data @NoArgsConstructor @AllArgsConstructor
public class VentaCreateRequest {

    @NotNull @Valid
    private VentaClienteRef cliente;

    @NotEmpty @Valid
    private List<VentaLineaCreate> lineas;
}