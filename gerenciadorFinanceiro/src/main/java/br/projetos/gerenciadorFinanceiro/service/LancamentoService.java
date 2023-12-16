package br.projetos.gerenciadorFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Lancamento;
import br.projetos.gerenciadorFinanceiro.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	private final LancamentoRepository lancamentoRepository;
	
	public LancamentoService(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}

	public Lancamento incluirLancamento(Lancamento lancamento) {
		return lancamentoRepository.save(lancamento);
	}

	public List<Lancamento> listaLancamentos() {
		return lancamentoRepository.findAll();
	}

	public Lancamento consultaLancamento(Long id) {
		return lancamentoRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundExcepttion(id));
		
	}

	public Lancamento alteraLancamento(Long id, Lancamento lancamento ) {
		return lancamentoRepository.findById(id)
				.map( recordFound -> {
					recordFound.setCartao( lancamento.getCartao() );
					recordFound.setCategoria( lancamento.getCategoria() );
					recordFound.setData( lancamento.getData() );
					recordFound.setDescricao( lancamento.getDescricao() );
					recordFound.setValor( lancamento.getValor() );
					return lancamentoRepository.save( recordFound );
				}).orElseThrow(() -> new RecordNotFoundExcepttion(id) );
	}

	public void excluiLancamento(Long id) {
		lancamentoRepository.delete( lancamentoRepository.findById( id )
				.orElseThrow( () -> new RecordNotFoundExcepttion(id)) );

	}

}
