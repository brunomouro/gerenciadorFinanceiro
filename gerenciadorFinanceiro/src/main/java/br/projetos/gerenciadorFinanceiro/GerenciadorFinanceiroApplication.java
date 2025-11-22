package br.projetos.gerenciadorFinanceiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "Gerenciador Financeiro API",
				version = "1.0",
				description = "API para controle de despesas e receitas"
				),
		servers = {
				@Server(url = "http://localhost:8080", description = "Ambiente local")
		}
)
@SpringBootApplication
@EnableCaching
public class GerenciadorFinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorFinanceiroApplication.class, args);
		
	}

}
