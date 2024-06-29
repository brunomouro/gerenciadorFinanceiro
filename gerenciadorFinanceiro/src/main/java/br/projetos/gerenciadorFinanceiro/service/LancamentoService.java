package br.projetos.gerenciadorFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.dto.mapper.LancamentoMapper;
import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.repository.LancamentoRepository;

@Service	
public class LancamentoService {
	
	private final LancamentoRepository lancamentoRepository;
	private final LancamentoMapper mapper;
	
	public LancamentoService(LancamentoRepository lancamentoRepository, LancamentoMapper mapper) {
		this.lancamentoRepository = lancamentoRepository;
		this.mapper = mapper;
	}

	public LancamentoDTO incluirLancamento(LancamentoDTO lancamento) {
		return  mapper.toDTO(lancamentoRepository.save(mapper.toEntity(lancamento)));
	}

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
					recordFound.setCartao( lancamento.cartao() );
					recordFound.setCategoria( lancamento.categoria() );
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

}
