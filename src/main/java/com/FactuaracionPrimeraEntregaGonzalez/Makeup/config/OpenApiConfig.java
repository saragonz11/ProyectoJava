package com.FactuaracionPrimeraEntregaGonzalez.Makeup.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI makeupBillingOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Makeup Billing API")
                        .description("API de facturación para la tienda de maquillaje.\n\n" +
                                "Permite gestionar clientes, productos y comprobantes de venta.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Sara Gonzalez")
                                .email("sglondono4421@gmail.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor local de desarrollo")
                ));
    }
}