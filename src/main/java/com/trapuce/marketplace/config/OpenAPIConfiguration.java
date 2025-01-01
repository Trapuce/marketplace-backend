package com.trapuce.marketplace.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * OpenAPI Configuration for Swagger/SpringDoc documentation
 * Defines server environments and API metadata for API documentation
 */
@Configuration
public class OpenAPIConfiguration {


    /**
     * Configures OpenAPI specification with server details and API information
     *
     * @return Fully configured OpenAPI object
     */
    @Bean
    public OpenAPI defineOpenApi() {
        Server devServer = new Server()
                .url("http://localhost:8080")
                .description("Development server URL");

        Server prodServer = new Server()
                .url("http://159.65.20.48:8080")
                .description("Production server URL");

        return new OpenAPI()
                .servers(Arrays.asList(devServer, prodServer))
                .info(new Info()
                        .title("Marketplace Mali SUGU")
                        .version("1.0")
                        .description("Comprehensive API for Mali SUGU"))
                .addSecurityItem(new SecurityRequirement().addList("bearerToken"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerToken", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }

}
