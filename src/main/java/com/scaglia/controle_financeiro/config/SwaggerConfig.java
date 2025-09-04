package com.scaglia.controle_financeiro.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Controle Financeiro API")
                        .description("Documentação da API para o sistema de Controle Financeiro")
                        .version("1.0.0"));
    }
}
