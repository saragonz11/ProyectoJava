package com.FactuaracionPrimeraEntregaGonzalez.Makeup.dao;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class BillingDao {

    @PersistenceContext
    private EntityManager em;

    public Cliente crearCliente(Cliente c) {
        em.persist(c);
        return c;
    }

    public Cliente buscarClientePorId(Long id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> buscarClientesPorNombre(String like) {
        return em.createQuery("SELECT c FROM Cliente c WHERE LOWER(c.nombre) LIKE LOWER(:q)", Cliente.class)
                .setParameter("q", "%" + like + "%")
                .getResultList();
    }

    public Producto crearProducto(Producto p) {
        em.persist(p);
        return p;
    }

    public Producto buscarProductoPorSku(String sku) {
        var list = em.createQuery("SELECT p FROM Producto p WHERE p.sku = :sku", Producto.class)
                .setParameter("sku", sku)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Producto> listarProductosActivos() {
        return em.createQuery("SELECT p FROM Producto p WHERE p.activo = true", Producto.class)
                .getResultList();
    }

    public Venta crearVenta(Cliente cliente, Map<Producto, Integer> items) {
        // Re-validar datos del cliente desde la sesión
        Cliente cManaged = em.find(Cliente.class, cliente.getId());
        if (cManaged == null) throw new IllegalArgumentException("Cliente no existe");

        Venta venta = new Venta();
        venta.setCliente(cManaged);

        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Producto, Integer> e : items.entrySet()) {
            Producto pManaged = em.find(Producto.class, e.getKey().getId());
            if (pManaged == null || !Boolean.TRUE.equals(pManaged.getActivo())) {
                throw new IllegalArgumentException("Producto inválido o inactivo: " + e.getKey().getId());
            }
            int qty = e.getValue();
            if (qty <= 0) throw new IllegalArgumentException("Cantidad inválida para producto " + pManaged.getSku());
            if (pManaged.getStock() < qty) throw new IllegalStateException("Stock insuficiente para " + pManaged.getSku());

            pManaged.setStock(pManaged.getStock() - qty);

            VentaItem item = new VentaItem();
            item.setProducto(pManaged);
            item.setCantidad(qty);
            item.setPrecioUnitario(pManaged.getPrecio());
            venta.addItem(item);
        }

        em.persist(venta);

        venta.getItems().forEach(VentaItem::getSubtotal);

        for (VentaItem it : venta.getItems()) {
            total = total.add(it.getSubtotal());
        }
        venta.setTotal(total);

        return venta;
    }

    public Venta buscarVenta(Long id) {
        return em.find(Venta.class, id);
    }

    public List<Venta> listarVentasDeCliente(Long clienteId) {
        return em.createQuery("SELECT v FROM Venta v WHERE v.cliente.id = :cid ORDER BY v.fecha DESC", Venta.class)
                .setParameter("cid", clienteId)
                .getResultList();
    }
}
