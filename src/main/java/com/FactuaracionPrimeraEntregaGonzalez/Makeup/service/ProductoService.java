package com.FactuaracionPrimeraEntregaGonzalez.Makeup.service;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.Producto;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.producto.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repo;

    public ProductoResponse create(ProductoRequest r){
        repo.findBySku(r.getSku()).ifPresent(x -> { throw new IllegalArgumentException("SKU ya existe"); });
        var p = new Producto(null, r.getSku(), r.getNombre(), r.getDescripcion(), r.getPrecio(),
                r.getStock(), r.getActivo()==null?true:r.getActivo(), LocalDateTime.now());
        return toResp(repo.save(p));
    }

    public ProductoResponse get(Long id){
        return toResp(repo.findById(id).orElseThrow(() -> new NoSuchElementException("Producto no encontrado")));
    }

    public List<ProductoResponse> list(Boolean activos){
        var data = (activos != null && activos) ? repo.findByActivoTrue() : repo.findAll();
        return data.stream().map(this::toResp).toList();
    }

    public ProductoResponse update(Long id, ProductoRequest r){
        var p = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
        if (!p.getSku().equalsIgnoreCase(r.getSku())) {
            repo.findBySku(r.getSku()).ifPresent(x -> { throw new IllegalArgumentException("SKU ya existe"); });
        }
        p.setSku(r.getSku()); p.setNombre(r.getNombre()); p.setDescripcion(r.getDescripcion());
        p.setPrecio(r.getPrecio()); p.setStock(r.getStock()); p.setActivo(r.getActivo()==null?true:r.getActivo());
        return toResp(repo.save(p));
    }

    public void delete(Long id){ repo.deleteById(id); }

    private ProductoResponse toResp(Producto p){
        return new ProductoResponse(p.getId(), p.getSku(), p.getNombre(), p.getDescripcion(),
                p.getPrecio(), p.getStock(), p.getActivo(), p.getCreadoEn());
    }
}
