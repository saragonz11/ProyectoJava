package com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name="productos",
        indexes={@Index(name="idx_producto_sku", columnList="sku", unique=true), @Index(name="idx_producto_nombre", columnList="nombre")})
@Data @NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=40, unique=true) private String sku;
    @Column(nullable=false, length=160) private String nombre;
    @Column(length=400) private String descripcion;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal precio;
    @Column(nullable=false) private Integer stock = 0;
    @Column(nullable=false) private Boolean activo = true;
    @Column(nullable=false) private LocalDateTime creadoEn = LocalDateTime.now();
}