package com.example.aluguelautomoveis.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API Aluguel de Automóveis",
        version = "1.0",
        description = "Documentação da API para o sistema de aluguel de automóveis"
    )
)
public class SwaggerConfig {
}
