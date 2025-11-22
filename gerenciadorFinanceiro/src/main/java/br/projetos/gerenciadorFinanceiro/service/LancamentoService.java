package br.projetos.gerenciadorFinanceiro.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CartaoMapper;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CategoriaMapper;
import br.projetos.gerenciadorFinanceiro.dto.mapper.LancamentoMapper;
import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Cartao;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Lancamento;
import br.projetos.gerenciadorFinanceiro.repository.CartaoRepository;
import br.projetos.gerenciadorFinanceiro.repository.CategoriaRepository;
import br.projetos.gerenciadorFinanceiro.repository.LancamentoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LancamentoService {
	
	private final LancamentoRepository lancamentoRepository;
	private final CategoriaRepository categoriaRepository;
	private final CartaoRepository cartaoRepository;
	
	private final LancamentoMapper mapper;
	
	public LancamentoService(LancamentoRepository lancamentoRepository, LancamentoMapper mapper, CategoriaRepository categoriaRepository, CartaoRepository cartaoRepository) {
		this.lancamentoRepository = lancamentoRepository;
		this.categoriaRepository = categoriaRepository;
		this.cartaoRepository = cartaoRepository;
		
		this.mapper = mapper;
	}

	@CacheEvict(value = "lancamentos", allEntries = true)
	public LancamentoDTO incluirLancamento(LancamentoDTO lancamento) {
		Categoria categoria = null;
		Cartao cartao = null;
		
		if ( lancamento.categoria() != null) {
			categoria = categoriaRepository.findById(lancamento.categoria().getId())
										   .orElseThrow(()-> new RecordNotFoundExcepttion("Categoria", lancamento.categoria().getId()));
		}
		
		if (lancamento.cartao().id() != null ) {
			cartao = cartaoRepository.findById(lancamento.cartao().id())
									 .orElseThrow(()-> new RecordNotFoundExcepttion("Cartão", lancamento.cartao().id()));
		}
		
		Lancamento lanc = mapper.toEntity(lancamento);
		lanc.setCategoria(categoria);
		lanc.setCartao(cartao);		
		
		return mapper.toDTO(lancamentoRepository.save(lanc));
	}

	@Cacheable("lancamentos")
	public List<LancamentoDTO> listaLancamentos() {
		List<LancamentoDTO> lancamentos = lancamentoRepository.findAll()
														   .stream()
														   .map(mapper::toDTO)
														   .toList();

		if( lancamentos.isEmpty() ) {
			throw new RecordNotFoundExcepttion();
		}
		return lancamentos;
	}

	public LancamentoDTO consultaLancamento(Long id) {
		return lancamentoRepository.findById(id)
				.map(mapper::toDTO)
				.orElseThrow(() -> new RecordNotFoundExcepttion(id));
		
	}

	public LancamentoDTO alteraLancamento(Long id, LancamentoDTO lancamento ) {
		return lancamentoRepository.findById(id)
				.map( recordFound -> {
					recordFound.setCartao( CartaoMapper.toEntity(lancamento.cartao()) );
					recordFound.setCategoria( CategoriaMapper.toEntity(lancamento.categoria()) );
					recordFound.setData( lancamento.data() );
					recordFound.setDescricao( lancamento.descricao() );
					recordFound.setValor( lancamento.valor() );
					return mapper.toDTO(lancamentoRepository.save( recordFound ));
				}).orElseThrow(() -> new RecordNotFoundExcepttion(id) );
	}

	public void excluiLancamento(Long id) {
		lancamentoRepository.delete( lancamentoRepository.findById( id )
				.orElseThrow( () -> new RecordNotFoundExcepttion(id)) );
	}

	public List<Lancamento> listaLancamentosPorData(String dataInicial, String dataFinal) {
		return lancamentoRepository.findBydataBetween(dataInicial, dataFinal);
	}
}
