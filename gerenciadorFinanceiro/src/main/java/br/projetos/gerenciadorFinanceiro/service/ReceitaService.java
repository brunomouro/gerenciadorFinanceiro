package br.projetos.gerenciadorFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Receita;
import br.projetos.gerenciadorFinanceiro.repository.ReceitaRepository;

@Service
public class ReceitaService {
	
	private final ReceitaRepository receitaRepository;
	
	public ReceitaService(ReceitaRepository receitaRepository) {
		this.receitaRepository = receitaRepository;
	}

	public Receita incluirReceita(Receita receita) {
		return receitaRepository.save( receita );
	}

	public List<Receita> listaReceitas() {
		List<Receita> receitas = receitaRepository.findAll();
		if( receitas.isEmpty() ) {
			throw new RecordNotFoundExcepttion();
		}
		return receitas;
	}

	public Receita consultaReceita(Long id) {
		return receitaRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundExcepttion(id));
	}
	
	public Receita alteraReceita(Long id, Receita receita) {
		return receitaRepository.findById(id)
				.map( recordFound -> {
					recordFound.setNome(receita.getNome());
					return receitaRepository.save(recordFound);
				}).orElseThrow(()-> new RecordNotFoundExcepttion(id));
	}

	public void excluiReceita(Long id) {
		receitaRepository.delete(
				receitaRepository.findById(id)
				.orElseThrow(()-> new RecordNotFoundExcepttion()));
	}

}
