package com.FactuaracionPrimeraEntregaGonzalez.Makeup;

import com.FactuaracionPrimeraEntregaGonzalez.Makeup.dao.BillingDao;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.Cliente;
import com.FactuaracionPrimeraEntregaGonzalez.Makeup.domain.Producto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class MakeupApplication {

	public static void main(String[] args) {
		SpringApplication.run(MakeupApplication.class, args);
	}

	@Bean
	CommandLineRunner seedData(BillingDao dao) {
		return args -> {
			// 1) Crear cliente
			Cliente ana = new Cliente();
			ana.setNombre("Ana Makeup Lover");
			ana.setEmail("ana@gmail.com");
			ana.setTelefono("+57 300 000 0000");
			ana.setDireccion("Cra 10 # 20-30, Bogotá");
			dao.crearCliente(ana);

			Cliente sara = new Cliente();
			sara.setNombre("Sara Gonzalez Londono");
			sara.setEmail("sara.gonzalez.londono@gmail.com");
			sara.setTelefono("+57 312 555 0142");
			sara.setDireccion("Calle 82 #12-50, Medellín");
			dao.crearCliente(sara);

			Cliente camila = new Cliente();
			camila.setNombre("Camila Restrepo");
			camila.setEmail("camila.restrepo@gmail.com");
			camila.setTelefono("+57 301 555 9801");
			camila.setDireccion("Carrera 7 # 72-41, Bogotá");
			dao.crearCliente(camila);

			Cliente valentina = new Cliente();
			valentina.setNombre("Valentina Mora");
			valentina.setEmail("valentina.mora@gmail.com");
			valentina.setTelefono("+57 320 555 2234");
			valentina.setDireccion("Av. 4N # 34-10, Cali");
			dao.crearCliente(valentina);

			Cliente laura = new Cliente();
			laura.setNombre("Laura Perez Rios");
			laura.setEmail("laura.perez.rios@gmail.com");
			laura.setTelefono("+57 315 555 6677");
			laura.setDireccion("Transv. 25 # 45-60, Bogotá");
			dao.crearCliente(laura);

			Cliente daniela = new Cliente();
			daniela.setNombre("Daniela Mejia");
			daniela.setEmail("daniela.mejia@gmail.com");
			daniela.setTelefono("+57 318 555 9090");
			daniela.setDireccion("Cra 43A # 1-50, El Poblado, Medellín");
			dao.crearCliente(daniela);

			// 2) Crear productos
			Producto labial = new Producto();
			labial.setSku("LIPS-MATTE-ROSE");
			labial.setNombre("Labial Matte Rose");
			labial.setDescripcion("Labial mate de larga duración tono rose.");
			labial.setPrecio(new BigDecimal("39900"));
			labial.setStock(100);
			dao.crearProducto(labial);

			Producto base = new Producto();
			base.setSku("BASE-LIQ-NEUTRA");
			base.setNombre("Base Líquida Neutra");
			base.setDescripcion("Cobertura media, acabado natural.");
			base.setPrecio(new BigDecimal("74900"));
			base.setStock(60);
			dao.crearProducto(base);

			// 3) Crear una venta (Ana compra 2 labiales y 1 base)
			Map<Producto, Integer> items = new LinkedHashMap<>();
			items.put(labial, 2);
			items.put(base, 1);

			var venta = dao.crearVenta(ana, items);

			System.out.println("Venta creada ID: " + venta.getId() + " Total: " + venta.getTotal());
		};
	}

}
