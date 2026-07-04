package br.projetos.gerenciadorFinanceiro.dto;

import java.math.BigDecimal;

public record GastosPorCategoriaDTO(String nome, BigDecimal disponivel, BigDecimal meta, BigDecimal gastos) {

}
