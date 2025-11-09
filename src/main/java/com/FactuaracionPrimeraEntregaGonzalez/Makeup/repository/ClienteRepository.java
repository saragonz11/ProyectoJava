package com.FactuaracionPrimeraEntregaGonzalez.Makeup.repository;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.Cliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);

    @Query("select c from Cliente c where lower(c.nombre) like lower(concat('%', :q, '%'))")
    List<Cliente> searchByNombre(@Param("q") String q);
}
