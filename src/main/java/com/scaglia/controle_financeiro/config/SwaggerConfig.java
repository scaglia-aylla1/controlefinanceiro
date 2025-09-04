package com.scaglia.controle_financeiro.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Controle Financeiro API")
                        .description("Documentação da API para o sistema de Controle Financeiro")
                        .version("v1.0")
                        .license(new License()
                                .name("Controle Financeiro"))
                        .contact(new Contact()
                                .name("Controle Financeiro")
                                .url("https://github.com/scaglia-aylla1/controlefinanceiro")
                                .email("aylla@scaglia.com.br")))
                .externalDocs(new ExternalDocumentation()
                        .description("Github")
                        .url("https://github.com/scaglia-aylla1"));
    }

    @Bean
    OpenApiCustomizer customizerGlobalHeaderOpenApiCustomizer(){
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations()
                    .forEach(operation -> {
                        ApiResponses apiResponses = operation.getResponses();

                        apiResponses.addApiResponse("200", createApiResponse("Sucesso"));
                        apiResponses.addApiResponse("201", createApiResponse("Objeto persistido"));
                        apiResponses.addApiResponse("204", createApiResponse("Objeto excluído"));
                        apiResponses.addApiResponse("400", createApiResponse("Erro na requisição"));
                        apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado"));
                        apiResponses.addApiResponse("403", createApiResponse("Acesso proibido"));
                        apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado"));
                        apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação"));
                    })
            );
        };
    }

    private ApiResponse createApiResponse(String message) {
        return new ApiResponse().description(message);
    }
}