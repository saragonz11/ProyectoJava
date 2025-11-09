package com.FactuaracionPrimeraEntregaGonzalez.Makeup.controller;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.ApiResponse;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.producto.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Validated
public class ProductoController {
    private final ProductoService service;

    @GetMapping
    public ApiResponse<List<ProductoResponse>> list(@RequestParam(required = false) Boolean activos){
        return new ApiResponse<>(true, "OK", service.list(activos));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductoResponse> get(@PathVariable Long id){
        return new ApiResponse<>(true, "OK", service.get(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductoResponse>> create(@Valid @RequestBody ProductoRequest r){
        var saved = service.create(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Creado", saved));
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductoResponse> update(@PathVariable Long id, @Valid @RequestBody ProductoRequest r){
        return new ApiResponse<>(true, "Actualizado", service.update(id, r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Eliminado", null));
    }
}
