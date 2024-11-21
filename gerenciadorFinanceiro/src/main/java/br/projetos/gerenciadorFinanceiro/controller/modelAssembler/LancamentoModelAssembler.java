package br.projetos.gerenciadorFinanceiro.controller.modelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.controller.LancamentosController;
import br.projetos.gerenciadorFinanceiro.controller.RequestContext;

@Component
public class LancamentoModelAssembler implements RepresentationModelAssembler<LancamentoModel, EntityModel<LancamentoModel>> {

	@Override
	public EntityModel<LancamentoModel> toModel(LancamentoModel entity) {
		EntityModel<LancamentoModel> lancamentoModel =  EntityModel.of(entity);
		
		lancamentoModel.add(linkTo(methodOn(LancamentosController.class).listaLancamentos()).withRel("lista-lancamentos"));
		lancamentoModel.add(linkTo(methodOn(LancamentosController.class).incluirLancamento(entity.getLancamento())).withRel("inclui-lancamento"));
		
		if(entity.getContext() != RequestContext.DELETE){
			lancamentoModel.add(linkTo(methodOn(LancamentosController.class).consultaLancamento(entity.getLancamento().id())).withSelfRel());
			lancamentoModel.add(linkTo(methodOn(LancamentosController.class).alteraLancamento(entity.getLancamento(), entity.getLancamento().id())).withRel("altera-lancamento"));
			lancamentoModel.add(linkTo(methodOn(LancamentosController.class).excluiLancamento(entity.getLancamento().id())).withRel("exclui-lancamento"));
		}		
		
		return lancamentoModel;
	}

}
