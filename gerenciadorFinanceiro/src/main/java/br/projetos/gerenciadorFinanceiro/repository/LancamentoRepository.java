package br.projetos.gerenciadorFinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.projetos.gerenciadorFinanceiro.model.Lancamento;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	 List<Lancamento> findBydataBetween(String startDate, String endDate);

}
