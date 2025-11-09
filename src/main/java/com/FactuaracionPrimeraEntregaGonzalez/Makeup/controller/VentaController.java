package com.FactuaracionPrimeraEntregaGonzalez.Makeup.controller;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.ApiResponse;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.venta.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Validated
public class VentaController {
    private final VentaService service;

    @GetMapping
    public ApiResponse<List<VentaResponse>> list(@RequestParam(required = false) Long clienteId){
        return new ApiResponse<>(true, "OK", service.list(clienteId));
    }

    @GetMapping("/{id}")
    public ApiResponse<VentaResponse> get(@PathVariable Long id){
        return new ApiResponse<>(true, "OK", service.get(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<VentaResponse>> create(@Valid @RequestBody VentaCreateRequest r){
        var saved = service.create(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Venta creada", saved));
    }
}
