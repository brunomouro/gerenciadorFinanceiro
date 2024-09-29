package br.projetos.gerenciadorFinanceiro.dto;

import br.projetos.gerenciadorFinanceiro.valids.ValidDate;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LancamentoDTO( @Id Long id, 
							 @NotNull @NotBlank @ValidDate String data, 
							 @NotBlank @NotNull String descricao, 
							 @Positive double valor, 
						  	 CategoriaDTO categoria, 
						  	 CartaoDTO cartao) {

}
