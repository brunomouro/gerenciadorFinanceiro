package br.projetos.gerenciadorFinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.projetos.gerenciadorFinanceiro.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>{

}
