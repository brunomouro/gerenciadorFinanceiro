package br.projetos.gerenciadorFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.repository.DespesaRepository;

@Service
public class DespesaService {
	
	private final DespesaRepository despesaRepository;
	
	public DespesaService(DespesaRepository categoriaRepository) {
		this.despesaRepository = categoriaRepository;
	}

	public Despesa incluirDespesa(Despesa despesa) {
		return despesaRepository.save( despesa );
	}

	public List<Despesa> listaDespesas() {
		List<Despesa> despesas = despesaRepository.findAll();
		if( despesas.isEmpty() ) {
			throw new RecordNotFoundExcepttion();
		}
		return despesas;
	}

	public Despesa consultaDespesa(Long id) {
		return despesaRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundExcepttion(id));
	}
	
	public Despesa alteraDespesa(Long id, Despesa despesa) {
		return despesaRepository.findById(id)
				.map( recordFound -> {
					recordFound.setNome(despesa.getNome());
					recordFound.setMeta(despesa.getMeta());
					return despesaRepository.save(recordFound);
				}).orElseThrow(()-> new RecordNotFoundExcepttion(id));
	}

	public void excluiDespesa(Long id) {
		despesaRepository.delete(
				despesaRepository.findById(id)
				.orElseThrow(()-> new RecordNotFoundExcepttion()));
	}

}
