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
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/categoria",
				produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE } )
@Validated
public class CategoriasController {

	private final CategoriaService categoriaService;
	private final CategoriaModelAssembler modelAssembler;
	
	public CategoriasController(CategoriaService categoriaService, CategoriaModelAssembler modelAssembler) {
		this.categoriaService = categoriaService;
		this.modelAssembler = modelAssembler;
	}
	
//	@PostMapping
//	@ResponseStatus(code = HttpStatus.CREATED)
//	public CategoriaDTO incluirDespesa( @RequestBody CategoriaDTO categoria ) {
//		return categoriaService.incluirCategoria( categoria );
//	}
	
	@PostMapping("inclui-categoria")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EntityModel<CategoriaModel> incluirDespesa( @RequestBody CategoriaDTO categoria ) {
		return modelAssembler.toModel(new CategoriaModel(categoriaService.incluirCategoria(categoria), RequestContext.CREATE));
	}
	
//	@GetMapping
//	public List<CategoriaDTO> listaCategorias(){
//		return categoriaService.listaCategorias();
//	}
	
	@GetMapping("lista-categorias")
	public CollectionModel<EntityModel<CategoriaModel>> listaCategorias(){
		List<EntityModel<CategoriaModel>> categorias = categoriaService.listaCategorias()
													   .stream()
													   .map(categoria -> modelAssembler.toModel(new CategoriaModel(categoria, RequestContext.GET))) 
													   .collect(Collectors.toList());
		return CollectionModel.of(categorias, linkTo(methodOn(CategoriasController.class).listaCategorias()).withSelfRel());
	}
	
//	@GetMapping("{id}")
//	public CategoriaDTO consultaCategoria( @PathVariable Long id ) {
//		return categoriaService.consultaCategoria( id );
//	}
	
	@GetMapping("consulta-categoria/{id}")
	public EntityModel<CategoriaModel> consultaCategoria( @PathVariable Long id ) {
		return modelAssembler.toModel(new CategoriaModel(categoriaService.consultaCategoria( id ), RequestContext.GET));
	}
	
//	@DeleteMapping("{id}")
//	public void excluiCategoria( @PathVariable Long id ) {
//		categoriaService.excluiCategoria( id );
//	}
	
	@DeleteMapping("exclui-categoria/{id}")
	public EntityModel<CategoriaModel> excluiCategoria( @PathVariable Long id ) {
		categoriaService.excluiCategoria( id );
		return modelAssembler.toModel(new CategoriaModel(id, RequestContext.DELETE));
	}
	
//	@PutMapping("{id}")
//	public CategoriaDTO alteraCategoria( @RequestBody CategoriaDTO categoria, @PathVariable Long id ) {
//		return categoriaService.alteraCategoria( id, categoria );
//	}
	
	@PutMapping("altera-categoria/{id}")
	public EntityModel<CategoriaModel> alteraCategoria( @RequestBody CategoriaDTO categoria, @PathVariable Long id ) {
		return modelAssembler.toModel(new CategoriaModel(categoriaService.alteraCategoria( id, categoria ), RequestContext.UPDATE));
	}
	
	@PutMapping("/meta/{id}")
	public DespesaDTOOut atualizaMeta(@RequestBody @Valid MetaDespesaDTO meta, @PathVariable Long id) {
		return categoriaService.atualizaMetaDespesa(id, meta);
	}
}
