package br.projetos.gerenciadorFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.DespesaDTO;
import br.projetos.gerenciadorFinanceiro.dto.ReceitaDTO;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CategoriaMapper;
import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.model.Receita;
import br.projetos.gerenciadorFinanceiro.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	private final CategoriaRepository categoriaRepository;
	
	public CategoriaService(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	public CategoriaDTO incluirCategoria(CategoriaDTO categoria) {
		return CategoriaMapper.toDTO(categoriaRepository.save(CategoriaMapper.toEntity(categoria)));
	}

	public List<CategoriaDTO> listaCategorias() {
		List<CategoriaDTO> categorias = categoriaRepository.findAll()
										.stream()
										.map(CategoriaMapper::toDTO)
										.toList();
		if( categorias.isEmpty() ) {
			throw new RecordNotFoundExcepttion();
		}
		return categorias;
	}

	public CategoriaDTO consultaCategoria(Long id) {
		return CategoriaMapper.toDTO(categoriaRepository.findById(id)
				  					 .orElseThrow(() -> new RecordNotFoundExcepttion(id))); 
	}
	
	public CategoriaDTO alteraCategoria(Long id, CategoriaDTO categoria) {
        Categoria c = categoriaRepository.findById(id)
        								 .orElseThrow(() -> new RecordNotFoundExcepttion(id));

        if (c instanceof Despesa) {
        	DespesaDTO ddto = (DespesaDTO) categoria;
        	Despesa despesa = (Despesa) c;
        	despesa.setNome( ddto.getNome() );
        	despesa.setMeta( ddto.getMeta() );
        	return CategoriaMapper.toDTO(categoriaRepository.save(despesa));
        }else {
        	if (c instanceof Receita) {
        		ReceitaDTO rdto = (ReceitaDTO) categoria;
        		Receita receita = (Receita) c;
        		receita.setNome(rdto.getNome());
        		return CategoriaMapper.toDTO(categoriaRepository.save(receita));
        	}
        }
        
        return null;
	}

	public void excluiCategoria(Long id) {
		categoriaRepository.delete(
				categoriaRepository.findById(id)
				.orElseThrow(()-> new RecordNotFoundExcepttion()));
	}

}
