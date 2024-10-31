package br.projetos.gerenciadorFinanceiro.dto;

import br.projetos.gerenciadorFinanceiro.valids.ValidDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FiltroGastosCategoriaDTO(@NotNull @NotBlank @ValidDate String dataInicial,
									   @NotNull @NotBlank @ValidDate String dataFinal) {

}
