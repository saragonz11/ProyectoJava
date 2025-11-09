package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.venta;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class VentaResponse {
    @Data @NoArgsConstructor @AllArgsConstructor
    public static class Item {
        private Long productoId;
        private String sku;
        private String nombreProducto;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private BigDecimal subtotal;
    }

    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private LocalDateTime fecha;
    private BigDecimal total;
    private List<Item> items;
}
