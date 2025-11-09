package com.FactuaracionPrimeraEntregaGonzalez.Makeup.service;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.Cliente;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.cliente.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repo;

    public ClienteResponse create(ClienteRequest r) {
        repo.findByEmail(r.getEmail()).ifPresent(x -> { throw new IllegalArgumentException("Email ya existe"); });
        Cliente c = new Cliente(null, r.getNombre(), r.getEmail(), r.getTelefono(), r.getDireccion(), LocalDateTime.now());
        return toResp(repo.save(c));
    }

    public ClienteResponse get(Long id){
        return toResp(repo.findById(id).orElseThrow(() -> new NoSuchElementException("Cliente no encontrado")));
    }

    public List<ClienteResponse> list(String q){
        var data = (q == null || q.isBlank()) ? repo.findAll() : repo.searchByNombre(q);
        return data.stream().map(this::toResp).toList();
    }

    public ClienteResponse update(Long id, ClienteRequest r){
        Cliente c = repo.findById(id).orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));
        if (!c.getEmail().equalsIgnoreCase(r.getEmail())) {
            repo.findByEmail(r.getEmail()).ifPresent(x -> { throw new IllegalArgumentException("Email ya existe"); });
        }
        c.setNombre(r.getNombre()); c.setEmail(r.getEmail());
        c.setTelefono(r.getTelefono()); c.setDireccion(r.getDireccion());
        return toResp(repo.save(c));
    }

    public void delete(Long id){ repo.deleteById(id); }

    private ClienteResponse toResp(Cliente c){
        return new ClienteResponse(c.getId(), c.getNombre(), c.getEmail(), c.getTelefono(), c.getDireccion(), c.getCreadoEn());
    }
}
