package br.projetos.gerenciadorFinanceiro.controller.modelAssembler;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.projetos.gerenciadorFinanceiro.controller.RequestContext;
import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;

public class CategoriaModel extends RepresentationModel<CategoriaModel> {
	private CategoriaDTO categoriaDTO;
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private RequestContext context;

	public CategoriaModel(CategoriaDTO categoriaDTO, Long id, RequestContext context) {
		this.categoriaDTO = categoriaDTO;
		this.id = id;
		this.context = context;
	}
	
	public CategoriaModel(CategoriaDTO categoriaDTO, RequestContext context) {
		this.categoriaDTO = categoriaDTO;
		this.context = context;
	}
	
	public CategoriaModel(Long id, RequestContext context) {
		this.id = id;
		this.context = context;
	}
	
	public CategoriaDTO getCategoria() {
		return categoriaDTO;
	}

	public Long getId() {
		return id;
	}

	public RequestContext getContext() {
		return context;
	}
	
	


}
