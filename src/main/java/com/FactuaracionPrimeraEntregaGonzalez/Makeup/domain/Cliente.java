package com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="clientes", indexes=@Index(name="idx_cliente_email", columnList="email", unique=true))
@Data @NoArgsConstructor @AllArgsConstructor
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=120) private String nombre;
    @Column(nullable=false, length=150, unique=true) private String email;
    @Column(length=30) private String telefono;
    @Column(length=200) private String direccion;
    @Column(nullable=false) private LocalDateTime creadoEn = LocalDateTime.now();
}
