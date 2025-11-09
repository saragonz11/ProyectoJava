package com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name="venta_items",
        uniqueConstraints=@UniqueConstraint(name="uk_venta_producto", columnNames={"venta_id","producto_id"}))
@Data @NoArgsConstructor @AllArgsConstructor
public class VentaItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="venta_id", nullable=false, foreignKey=@ForeignKey(name="fk_item_venta"))
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Venta venta;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="producto_id", nullable=false, foreignKey=@ForeignKey(name="fk_item_producto"))
    private Producto producto;

    @Column(nullable=false) private Integer cantidad;
    @Column(nullable=false, precision=12, scale=2) private BigDecimal precioUnitario;
    @Column(nullable=false, precision=14, scale=2) private BigDecimal subtotal;

    @PrePersist @PreUpdate
    private void calcSubtotal() {
        if (precioUnitario != null && cantidad != null) {
            subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        }
    }
}
