package br.projetos.gerenciadorFinanceiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.projetos.gerenciadorFinanceiro.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	@Query("SELECT c FROM Categoria c WHERE TYPE(c) = Despesa")
    List<Categoria> findAllDespesas();
}
