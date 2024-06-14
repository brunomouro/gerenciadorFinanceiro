package br.projetos.gerenciadorFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	private final CategoriaRepository categoriaRepository;
	
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	public Categoria incluirCategoria(Categoria categoria) {
		return categoriaRepository.save( categoria );
	}

	public List<Categoria> listaCategorias() {
		List<Categoria> despesas = categoriaRepository.findAll();
		if( despesas.isEmpty() ) {
			throw new RecordNotFoundExcepttion();
		}
		return despesas;
	}

	public Categoria consultaCategoria(Long id) {
		return categoriaRepository.findById(id)
								  .orElseThrow(() -> new RecordNotFoundExcepttion(id));
	}
	
	public Categoria alteraDespesa(Long id, Despesa categoria) {
        Categoria d = categoriaRepository.findById(id)
        								 .orElseThrow(() -> new RecordNotFoundExcepttion(id));

        if (d instanceof Despesa) {
        	Despesa despesa = (Despesa) d;
        	despesa.setNome( categoria.getNome() );
        	despesa.setMeta( categoria.getMeta() );
        	return categoriaRepository.save(despesa);
        }else {
            throw new IllegalArgumentException("A categoria com o id " + id + " não é uma despesa");
        }
	}

	public void excluiCategoria(Long id) {
		categoriaRepository.delete(
				categoriaRepository.findById(id)
				.orElseThrow(()-> new RecordNotFoundExcepttion()));
	}

}
