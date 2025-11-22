package br.projetos.gerenciadorFinanceiro.controller.modelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.controller.CategoriasController;
import br.projetos.gerenciadorFinanceiro.controller.RequestContext;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaModel, EntityModel<CategoriaModel>> {

	@Override
	public EntityModel<CategoriaModel> toModel(CategoriaModel entity) {
		EntityModel<CategoriaModel> categoriaModel =  EntityModel.of(entity);
		
		categoriaModel.add(linkTo(methodOn(CategoriasController.class).listaCategorias()).withRel("lista-categorias"));
		categoriaModel.add(linkTo(methodOn(CategoriasController.class).incluirDespesa(entity.getCategoria())).withRel("inclui-categoria"));
		
		if(entity.getContext() != RequestContext.DELETE){
			categoriaModel.add(linkTo(methodOn(CategoriasController.class).consultaCategoria(entity.getCategoria().getId())).withSelfRel());
			categoriaModel.add(linkTo(methodOn(CategoriasController.class).alteraCategoria(entity.getCategoria(), entity.getCategoria().getId())).withRel("altera-categoria"));
			categoriaModel.add(linkTo(methodOn(CategoriasController.class).excluiCategoria(entity.getCategoria().getId())).withRel("exclui-categoria"));
		}		
		
		return categoriaModel;
	}

}
