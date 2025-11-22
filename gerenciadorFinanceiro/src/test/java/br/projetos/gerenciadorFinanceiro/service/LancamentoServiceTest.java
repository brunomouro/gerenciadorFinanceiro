package br.projetos.gerenciadorFinanceiro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Description;

import br.projetos.gerenciadorFinanceiro.dto.CartaoDTO;
import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CartaoMapper;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CategoriaMapper;
import br.projetos.gerenciadorFinanceiro.dto.mapper.LancamentoMapper;
import br.projetos.gerenciadorFinanceiro.enums.Status;
import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Cartao;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.model.Lancamento;
import br.projetos.gerenciadorFinanceiro.repository.CartaoRepository;
import br.projetos.gerenciadorFinanceiro.repository.CategoriaRepository;
import br.projetos.gerenciadorFinanceiro.repository.LancamentoRepository;

class LancamentoServiceTest {
	@Mock
	private LancamentoRepository lancamentoRepository;
	
	@Mock
	private CategoriaRepository categoriaRepository;
	
    @Mock
	private CartaoRepository cartaoRepository;
	
    @Mock
	private LancamentoMapper mapper;
    
    @InjectMocks
    private LancamentoService lancamentoService;
    
    private Lancamento lancamento;
    private Categoria categoria;
    private Cartao cartao;
    
    private CategoriaDTO categoriaDTO;
    private CartaoDTO cartaoDTO;
    private LancamentoDTO lancDTO;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks( this );
		
		categoria = new Despesa(1L, "Alimentacao", Status.ATIVO, Double.valueOf(100));
		cartao = new Cartao(1L, "Visa", Status.ATIVO);
		lancamento = new Lancamento(1L,
									"12/09/2025",
									130,
									"Sushi",
									categoria,
									cartao);
		
		categoriaDTO = CategoriaMapper.toDTO(categoria);
		
		cartaoDTO = CartaoMapper.toDTO(cartao);
		
		lancDTO = new LancamentoDTO(1L,
									"12/09/2025",
									"Sushi",
									130,
									categoriaDTO,
									cartaoDTO);
	}

	@Test
	@Description("Cria um novo lancamento com sucesso")
	void createLancamentoSucess() {
		
		when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
		when(cartaoRepository.findById(1L)).thenReturn(Optional.of(cartao));
		when(mapper.toEntity(lancDTO)).thenReturn(lancamento);
		when(lancamentoRepository.save(lancamento)).thenReturn(lancamento);
		when(mapper.toDTO(lancamento)).thenReturn(lancDTO);
		
		LancamentoDTO result = lancamentoService.incluirLancamento(lancDTO);
		
		assertNotNull(result);
		assertEquals(lancDTO.id(), result.id());
		assertEquals(lancDTO.data(), result.data());
		assertEquals(lancDTO.descricao(), result.descricao());
		assertEquals(lancDTO.valor(), result.valor());
		assertEquals(lancDTO.categoria(), result.categoria());
		assertEquals(lancDTO.cartao(), result.cartao());
		
		verify(categoriaRepository).findById(1L);
		verify(cartaoRepository).findById(1L);
		verify(lancamentoRepository).save(lancamento);
		verify(mapper).toEntity(lancDTO);
		verify(mapper).toDTO(lancamento);		
	}
	
	@Test
	void createLancamentoCategoriaNotFound() {
		when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception ex = assertThrows(RecordNotFoundExcepttion.class, () -> lancamentoService.incluirLancamento(lancDTO));
		
		assertTrue(ex.getMessage().contains("Nao existe Categoria com o id: 1"));
	}
	
	@Test
	void createLancamentoCartaoNotFound() {
		when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
		when(cartaoRepository.findById(1L)).thenReturn(Optional.empty());
		
		Exception ex = assertThrows(RecordNotFoundExcepttion.class, () -> lancamentoService.incluirLancamento(lancDTO));
		
		assertTrue(ex.getMessage().contains("Nao existe Cartão com o id: 1"));
	}

}
