package br.projetos.gerenciadorFinanceiro.service;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.model.Lancamento;
import br.projetos.gerenciadorFinanceiro.repository.LancamentoRepository;

@Service
public class LancamentoService {
	
	private final LancamentoRepository lancamentoRepository;
	
	public LancamentoService(LancamentoRepository lancamentoRepository) {
		this.lancamentoRepository = lancamentoRepository;
	}

	public Lancamento incluirLancamento(Lancamento lancamento) {
		lancamentoRepository.save(lancamento);
		return null;
	}

}
