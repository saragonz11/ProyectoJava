package com.FactuaracionPrimeraEntregaGonzalez.Makeup.controller;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.ApiResponse;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.cliente.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
public class ClienteController {
    private final ClienteService service;

    @GetMapping
    public ApiResponse<List<ClienteResponse>> list(@RequestParam(required = false) String q){
        return new ApiResponse<>(true, "OK", service.list(q));
    }

    @GetMapping("/{id}")
    public ApiResponse<ClienteResponse> get(@PathVariable Long id){
        return new ApiResponse<>(true, "OK", service.get(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> create(@Valid @RequestBody ClienteRequest r){
        var saved = service.create(r);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "Creado", saved));
    }

    @PutMapping("/{id}")
    public ApiResponse<ClienteResponse> update(@PathVariable Long id, @Valid @RequestBody ClienteRequest r){
        return new ApiResponse<>(true, "Actualizado", service.update(id, r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Eliminado", null));
    }
}
