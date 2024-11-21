package br.projetos.gerenciadorFinanceiro.controller.modelAssembler;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.projetos.gerenciadorFinanceiro.controller.RequestContext;
import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;

public class LancamentoModel extends RepresentationModel<LancamentoModel>{
	private LancamentoDTO lancamentoDTO;
	@JsonIgnore
	private Long id;
	@JsonIgnore
	private RequestContext context;

	public LancamentoModel(LancamentoDTO lancamentoDTO, Long id, RequestContext context) {
		this.lancamentoDTO = lancamentoDTO;
		this.id = id;
		this.context = context;
	}
	
	public LancamentoModel(LancamentoDTO lancamentoDTO, RequestContext context) {
		this.lancamentoDTO = lancamentoDTO;
		this.context = context;
	}
	
	public LancamentoModel(Long id, RequestContext context) {
		this.id = id;
		this.context = context;
	}
	
	public LancamentoDTO getLancamento() {
		return lancamentoDTO;
	}

	public Long getId() {
		return id;
	}

	public RequestContext getContext() {
		return context;
	}
	
	
}
