package br.projetos.gerenciadorFinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.projetos.gerenciadorFinanceiro.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

}
