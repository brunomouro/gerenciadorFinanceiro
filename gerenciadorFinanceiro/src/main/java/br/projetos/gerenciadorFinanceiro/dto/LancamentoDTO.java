package br.projetos.gerenciadorFinanceiro.dto;

import br.projetos.gerenciadorFinanceiro.model.Cartao;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LancamentoDTO( @Id Long id, 
							 @NotBlank @NotNull String data, 
							 @NotBlank @NotNull String descricao, 
							 @Positive double valor, 
						  	 Categoria categoria, 
						  	 Cartao cartao) {

}
