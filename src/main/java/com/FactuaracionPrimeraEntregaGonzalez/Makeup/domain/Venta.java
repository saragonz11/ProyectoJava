package com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity @Table(name="ventas", indexes=@Index(name="idx_venta_fecha", columnList="fecha"))
@Data @NoArgsConstructor @AllArgsConstructor
public class Venta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="cliente_id", nullable=false, foreignKey=@ForeignKey(name="fk_venta_cliente"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Cliente cliente;

    @Column(nullable=false) private LocalDateTime fecha = LocalDateTime.now();
    @Column(nullable=false, precision=14, scale=2) private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy="venta", cascade=CascadeType.ALL, orphanRemoval=true)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<VentaItem> items = new ArrayList<>();
}
