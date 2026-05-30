package br.projetos.gerenciadorFinanceiro.unittests.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Description;

import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.DespesaDTO;
import br.projetos.gerenciadorFinanceiro.dto.MetaDespesaDTO;
import br.projetos.gerenciadorFinanceiro.dto.ReceitaDTO;
import br.projetos.gerenciadorFinanceiro.enums.Status;
import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.model.Receita;
import br.projetos.gerenciadorFinanceiro.repository.CategoriaRepository;
import br.projetos.gerenciadorFinanceiro.service.CategoriaService;

public class CategoriaServiceTest {
	
	@Mock
	private CategoriaRepository repository;
	
	@InjectMocks
	private CategoriaService service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks( this );
	}
	
	@Test
	@Description("Cria categoria de despesa com sucesso")
	void createDespesaSucess() {
		CategoriaDTO dto = new DespesaDTO();
		dto.setId(1L);
		dto.setNome("Market");
		
		Despesa categoria = new Despesa();
		categoria.setId(1L);
		categoria.setNome("Market");
		categoria.setStatus(Status.ATIVO);
		
		when(repository.save(any())).thenReturn(categoria);
		
		CategoriaDTO result = service.incluirCategoria(dto);
		
		assertNotNull(result);
		assertEquals(result.getId(), dto.getId());
		assertEquals(result.getNome(), dto.getNome());
		
		verify(repository).save(any());		
	}
	
	@Test
	@Description("Cria categoria de receita com sucesso")
	void criaReceitaSucess() {
		CategoriaDTO dto = new ReceitaDTO();
		dto.setId(1L);
		dto.setNome("Salario");
		
		Receita categoria = new Receita();
		categoria.setId(1L);
		categoria.setNome("Salario");
		categoria.setStatus(Status.ATIVO);
		
		when(repository.save(any())).thenReturn(categoria);
		
		CategoriaDTO result = service.incluirCategoria(dto);
		
		assertNotNull(result);
		assertEquals(result.getId(), dto.getId());
		assertEquals(result.getNome(), dto.getNome());
		
		verify(repository).save(any());
	}
	
	@Test
	@Description("Updates category's name with sucess")
	void shouldUpdateCategoria() {
		Despesa categoriaCreated = new Despesa();
		categoriaCreated.setId(1L);
		categoriaCreated.setNome("Market");
		categoriaCreated.setStatus(Status.ATIVO);
		
		CategoriaDTO dtoCategoriaUpdated = new DespesaDTO();
		dtoCategoriaUpdated.setId(1L);
		dtoCategoriaUpdated.setNome("Fuel");
		
		Despesa categoriaUpdated = new Despesa();
		categoriaUpdated.setId(1L);
		categoriaUpdated.setNome("Fuel");
		categoriaUpdated.setStatus(Status.ATIVO);
		
		when(repository.findById(dtoCategoriaUpdated.getId())).thenReturn(Optional.of(categoriaCreated));
		when(repository.save(any(Despesa.class))).thenReturn(categoriaUpdated);
		
		CategoriaDTO result = service.alteraCategoria(dtoCategoriaUpdated.getId(), dtoCategoriaUpdated);
		
		assertNotNull(result);
		assertEquals(result.getId(), dtoCategoriaUpdated.getId());
		assertEquals(result.getNome(), dtoCategoriaUpdated.getNome());
		
		verify(repository).save(any(Despesa.class));		
		verify(repository).findById(1L);
	}
	
	@Test
	@Description("Should throws exception when updating category not found")
	void shouldThrowExceptionWhenUpdatingCategoriaNotFound() {
		CategoriaDTO dtoCategoriaUpdated = new DespesaDTO();
		dtoCategoriaUpdated.setId(1L);
		dtoCategoriaUpdated.setNome("Fuel");		
		
		assertThatThrownBy(() -> service.alteraCategoria(dtoCategoriaUpdated.getId(), dtoCategoriaUpdated))
        .isInstanceOf(RecordNotFoundExcepttion.class);
		
		verify(repository, never()).save(any());
	}
	
	@Test
	@Description("Should throws exception when deleting category not found")
	void shouldThrowExceptionWhenDeletingCategoriaNotFound() {
		CategoriaDTO dtoCategoria = new DespesaDTO();
		dtoCategoria.setId(1L);
		dtoCategoria.setNome("Fuel");		
		
		assertThatThrownBy(() -> service.excluiCategoria(dtoCategoria.getId()))
        .isInstanceOf(RecordNotFoundExcepttion.class);
		
		verify(repository, never()).delete(any());		
	}

	@Test
	void shouldDeleteCategoriaSucess() {
	    Despesa despesa = new Despesa();
	    despesa.setId(1L);

	    when(repository.findById(1L)).thenReturn(Optional.of(despesa));

	    service.excluiCategoria(1L);

	    verify(repository).findById(1L);
	    verify(repository).delete(despesa);
	}
	
	@Test
	@Description("Should throws exception when updating goals for categoria not found")
	void shouldThrowExceptionWhenUpdateExpenseGoalsForCategoriaNotFound() {
		CategoriaDTO dtoCategoria = new DespesaDTO();
		dtoCategoria.setId(1L);
		dtoCategoria.setNome("Fuel");
		
		MetaDespesaDTO meta = new MetaDespesaDTO(100.0);
		
		assertThatThrownBy(() -> service.atualizaMetaDespesa(dtoCategoria.getId(), meta))
        .isInstanceOf(RecordNotFoundExcepttion.class);
		
		verify(repository, never()).save(any());
	}
	
	//TESTE: Atualiza meta de despesa com ID de categoria de receita
	@Test
	@Description( "Should throws exception when updating expense goal for a Receita")
	void shouldThrowExceptionWhenUpdatingExpenseGoalForCategoriaReceita() {
		Long id = 1L;
		
		Receita receita = new Receita();
		receita.setId(id);
		receita.setNome("Salario");
		
		when(repository.findById(id)).thenReturn(Optional.of(receita));
		
		assertThatThrownBy(() -> service.atualizaMetaDespesa(id, new MetaDespesaDTO(100.0)))
	    .isInstanceOf(RecordNotFoundExcepttion.class)
	    .hasMessageContaining("Receita");

		verify(repository, never()).save(any(Despesa.class));
	}
	
	//TESTE: Atualiza meta de despesa com sucesso

}
