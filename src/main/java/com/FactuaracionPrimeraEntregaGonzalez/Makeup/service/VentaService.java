package com.FactuaracionPrimeraEntregaGonzalez.Makeup.service;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dto.venta.*;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.repository.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Data
class WorldClockResponse {
    private String currentDateTime;
}

@Service
@RequiredArgsConstructor
@Transactional
public class VentaService {

    private final VentaRepository ventaRepo;
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    private LocalDateTime obtenerFechaComprobanteUtc() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://worldclockapi.com/api/json/utc/now";
            ResponseEntity<WorldClockResponse> resp =
                    restTemplate.getForEntity(url, WorldClockResponse.class);

            WorldClockResponse body = resp.getBody();
            if (resp.getStatusCode().is2xxSuccessful()
                    && body != null
                    && body.getCurrentDateTime() != null) {

                return OffsetDateTime.parse(body.getCurrentDateTime())
                        .toLocalDateTime();
            }
        } catch (Exception ex) {
        }

        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public VentaResponse create(VentaCreateRequest r) {
        Long clienteId = r.getCliente().getClienteId();

        Cliente cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new NoSuchElementException("Cliente no encontrado"));

        Venta venta = new Venta();
        venta.setCliente(cliente);

        venta.setFecha(obtenerFechaComprobanteUtc());

        List<VentaItem> items = new ArrayList<>();

        for (VentaLineaCreate linea : r.getLineas()) {
            Long productoId = linea.getProducto().getProductoId();

            Producto p = productoRepo.findById(productoId)
                    .orElseThrow(() -> new NoSuchElementException("Producto no encontrado: " + productoId));

            if (Boolean.FALSE.equals(p.getActivo())) {
                throw new IllegalArgumentException("Producto inactivo: " + p.getSku());
            }

            if (p.getStock() < linea.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para " + p.getSku());
            }

            p.setStock(p.getStock() - linea.getCantidad());

            VentaItem vi = new VentaItem();
            vi.setProducto(p);
            vi.setCantidad(linea.getCantidad());
            vi.setPrecioUnitario(p.getPrecio());
            vi.setVenta(venta);

            items.add(vi);
        }

        venta.setItems(items);
        venta = ventaRepo.save(venta);

        BigDecimal total = BigDecimal.ZERO;
        for (VentaItem vi : venta.getItems()) {
            total = total.add(vi.getSubtotal());
        }
        venta.setTotal(total);

        return toResp(venta);
    }

    public VentaResponse get(Long id) {
        Venta v = ventaRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Venta no encontrada"));
        return toResp(v);
    }

    public List<VentaResponse> list(Long clienteId) {
        List<Venta> data = (clienteId == null)
                ? ventaRepo.findAll()
                : ventaRepo.findByClienteIdOrderByFechaDesc(clienteId);
        return data.stream().map(this::toResp).toList();
    }

    private VentaResponse toResp(Venta v) {
        List<VentaResponse.Item> items = v.getItems().stream()
                .map(it -> new VentaResponse.Item(
                        it.getProducto().getId(),
                        it.getProducto().getSku(),
                        it.getProducto().getNombre(),
                        it.getCantidad(),
                        it.getPrecioUnitario(),
                        it.getSubtotal()
                ))
                .toList();

        int cantidadTotalProductos = v.getItems().stream()
                .mapToInt(VentaItem::getCantidad)
                .sum();

        return new VentaResponse(
                v.getId(),
                v.getCliente().getId(),
                v.getCliente().getNombre(),
                v.getFecha(),
                v.getTotal(),
                cantidadTotalProductos,
                items
        );
    }
}