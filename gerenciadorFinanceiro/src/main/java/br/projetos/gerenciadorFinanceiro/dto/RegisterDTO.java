package br.projetos.gerenciadorFinanceiro.dto;

import br.projetos.gerenciadorFinanceiro.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
