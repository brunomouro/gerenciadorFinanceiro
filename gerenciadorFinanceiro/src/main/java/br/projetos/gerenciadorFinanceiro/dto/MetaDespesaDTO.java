package br.projetos.gerenciadorFinanceiro.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MetaDespesaDTO(@Positive @NotNull double valorMeta) {

}
