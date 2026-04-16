package com.example.Cheminova.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("Authorization")
                                        .description("Enter JWT token: ")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(
                    new Info()
                        .title("Cheminova API")
                        .version("1.0")
                        .contact(
                                new Contact()
                                        .name("Mohd Junaid")
                        )
                        .description("API documentation for Cheminova application"))
                .servers(
                        List.of(
                                new Server()
                                        .url("https://cheminova-ai-production.up.railway.app")
                                        .description("Production Server"),
                                new Server()
                                        .url("http://localhost:9215")
                                        .description("Local Development Server")
                        ))
                ;
    }
}
