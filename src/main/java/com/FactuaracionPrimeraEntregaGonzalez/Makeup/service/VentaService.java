package com.FactuaracionPrimeraEntregaGonzalez.Makeup.service;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.venta.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service @RequiredArgsConstructor
@Transactional
public class VentaService {
    private final VentaRepository ventaRepo;
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    public VentaResponse create(VentaCreateRequest r){
        var cliente = clienteRepo.findById(r.getClienteId())
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));

        var venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());

        var items = new ArrayList<VentaItem>();
        for (var it : r.getItems()) {
            var p = productoRepo.findById(it.getProductoId())
                    .orElseThrow(() -> new NoSuchElementException("Producto no encontrado: " + it.getProductoId()));
            if (Boolean.FALSE.equals(p.getActivo())) throw new IllegalArgumentException("Producto inactivo: " + p.getSku());
            if (p.getStock() < it.getCantidad()) throw new IllegalStateException("Stock insuficiente para " + p.getSku());
            p.setStock(p.getStock() - it.getCantidad());

            var vi = new VentaItem();
            vi.setProducto(p);
            vi.setCantidad(it.getCantidad());
            vi.setPrecioUnitario(p.getPrecio()); // snapshot
            vi.setVenta(venta);
            items.add(vi);
        }

        venta.setItems(items);
        venta = ventaRepo.save(venta);

        // total (subtotales ya en @PrePersist)
        BigDecimal total = BigDecimal.ZERO;
        for (var vi : venta.getItems()) total = total.add(vi.getSubtotal());
        venta.setTotal(total);

        return toResp(venta);
    }

    public VentaResponse get(Long id){
        var v = ventaRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Venta no encontrada"));
        return toResp(v);
    }

    public List<VentaResponse> list(Long clienteId){
        var data = (clienteId == null) ? ventaRepo.findAll() : ventaRepo.findByClienteIdOrderByFechaDesc(clienteId);
        return data.stream().map(this::toResp).toList();
    }

    private VentaResponse toResp(Venta v){
        var items = v.getItems().stream().map(it ->
                new VentaResponse.Item(
                        it.getProducto().getId(),
                        it.getProducto().getSku(),
                        it.getProducto().getNombre(),
                        it.getCantidad(),
                        it.getPrecioUnitario(),
                        it.getSubtotal()
                )).toList();

        return new VentaResponse(v.getId(), v.getCliente().getId(), v.getCliente().getNombre(),
                v.getFecha(), v.getTotal(), items);
    }
}
