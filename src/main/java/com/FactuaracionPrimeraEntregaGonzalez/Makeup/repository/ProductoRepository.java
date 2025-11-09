package com.FactuaracionPrimeraEntregaGonzalez.Makeup.repository;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findBySku(String sku);
    List<Producto> findByActivoTrue();
}
