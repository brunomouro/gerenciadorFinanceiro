package br.projetos.gerenciadorFinanceiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth"; // Pode ser qualquer nome

        return new OpenAPI()
                // Adiciona o componente de segurança (o cadeado)
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP) // O tipo é HTTP
                                                .scheme("bearer") // O esquema é bearer
                                                .bearerFormat("JWT") // O formato é JWT
                                )
                )
                // Adiciona o requisito de segurança para todas as rotas
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // (Opcional) Informações da sua API
                .info(new Info().title("Nome da Sua API").version("v1"));
    }
}
