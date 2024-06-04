package br.projetos.gerenciadorFinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.projetos.gerenciadorFinanceiro.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

}
