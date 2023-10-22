package br.projetos.gerenciadorFinanceiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GerenciadorFinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciadorFinanceiroApplication.class, args);
		
		System.out.println("Ola mundo");
	}

}
