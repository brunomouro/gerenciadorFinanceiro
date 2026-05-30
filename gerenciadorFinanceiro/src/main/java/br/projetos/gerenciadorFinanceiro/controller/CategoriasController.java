package br.projetos.gerenciadorFinanceiro.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.projetos.gerenciadorFinanceiro.controller.modelAssembler.CategoriaModel;
import br.projetos.gerenciadorFinanceiro.controller.modelAssembler.CategoriaModelAssembler;
import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.DespesaDTOOut;
import br.projetos.gerenciadorFinanceiro.dto.MetaDespesaDTO;
import br.projetos.gerenciadorFinanceiro.service.CategoriaService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/categoria",
				produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
@Validated
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success", content = @Content(schema = @Schema(implementation = CategoriaDTO.class))),
	    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
	    @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
	    @ApiResponse(responseCode = "403", description = "Proibido", content = @Content),
	    @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content),
	    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
public class CategoriasController {

	private final CategoriaService categoriaService;
	private final CategoriaModelAssembler modelAssembler;
	
	public CategoriasController(CategoriaService categoriaService, CategoriaModelAssembler modelAssembler) {
		this.categoriaService = categoriaService;
		this.modelAssembler = modelAssembler;
	}
	
	@PostMapping("inclui-categoria")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EntityModel<CategoriaModel> incluirDespesa( @RequestBody CategoriaDTO categoria ) {
		return modelAssembler.toModel(new CategoriaModel(categoriaService.incluirCategoria(categoria), RequestContext.CREATE));
	}
	
	@GetMapping("lista-categorias")
	public CollectionModel<EntityModel<CategoriaModel>> listaCategorias(){
		List<EntityModel<CategoriaModel>> categorias = categoriaService.listaCategorias()
													   .stream()
													   .map(categoria -> modelAssembler.toModel(new CategoriaModel(categoria, RequestContext.GET))) 
													   .collect(Collectors.toList());
		return CollectionModel.of(categorias, linkTo(methodOn(CategoriasController.class).listaCategorias()).withSelfRel());
	}
	
	@GetMapping("consulta-categoria/{id}")
	public EntityModel<CategoriaModel> consultaCategoria( @PathVariable Long id ) {
		return modelAssembler.toModel(new CategoriaModel(categoriaService.consultaCategoria( id ), RequestContext.GET));
	}
	
	@DeleteMapping("exclui-categoria/{id}")
	public EntityModel<CategoriaModel> excluiCategoria( @PathVariable Long id ) {
		categoriaService.excluiCategoria( id );
		return modelAssembler.toModel(new CategoriaModel(id, RequestContext.DELETE));
	}
	
	@PutMapping("altera-categoria/{id}")
	public EntityModel<CategoriaModel> alteraCategoria( @RequestBody CategoriaDTO categoria, @PathVariable Long id ) {
		return modelAssembler.toModel(new CategoriaModel(categoriaService.alteraCategoria( id, categoria ), RequestContext.UPDATE));
	}
	
	@PutMapping("/meta/{id}")
	public DespesaDTOOut atualizaMeta(@RequestBody @Valid MetaDespesaDTO meta, @PathVariable Long id) {
		return categoriaService.atualizaMetaDespesa(id, meta);
	}
}
