package br.projetos.gerenciadorFinanceiro.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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

import br.projetos.gerenciadorFinanceiro.controller.modelAssembler.LancamentoModel;
import br.projetos.gerenciadorFinanceiro.controller.modelAssembler.LancamentoModelAssembler;
import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.service.LancamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/lancamento")
@Validated
public class LancamentosController {
	
	private final LancamentoService lancamentoService;
	private final LancamentoModelAssembler modelAssembler;
	
	public LancamentosController(LancamentoService lancamentoService, LancamentoModelAssembler modelAssembler) {
		this.lancamentoService = lancamentoService;
		this.modelAssembler = modelAssembler;
	}
	
	@PostMapping("inclui-lancamento")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EntityModel<LancamentoModel> incluirLancamento( @RequestBody @Valid LancamentoDTO lancamento ) {
		return modelAssembler.toModel(new LancamentoModel(lancamentoService.incluirLancamento(lancamento), RequestContext.CREATE));
	}
	
	@GetMapping("lista-lancamentos")
	public CollectionModel<EntityModel<LancamentoModel>> listaLancamentos(){
		List<EntityModel<LancamentoModel>> lancamentos = lancamentoService.listaLancamentos()
																		  .stream()
			      														  .map(lancamento -> modelAssembler.toModel(new LancamentoModel(lancamento, RequestContext.GET))) 
			      														  .collect(Collectors.toList());
		return CollectionModel.of(lancamentos, linkTo(methodOn(LancamentosController.class).listaLancamentos()).withSelfRel());
	}
	
	@GetMapping("consulta-lancamento/{id}")
	public EntityModel<LancamentoModel> consultaLancamento( @PathVariable @Positive Long id ) {
		return modelAssembler.toModel(new LancamentoModel(lancamentoService.consultaLancamento( id ), RequestContext.GET));
	}
	
	@PutMapping("altera-lancamento/{id}")
	public EntityModel<LancamentoModel> alteraLancamento( @RequestBody @Valid LancamentoDTO lancamento, @PathVariable @Positive Long id ) {
		return modelAssembler.toModel(new LancamentoModel(lancamentoService.alteraLancamento( id, lancamento ), RequestContext.UPDATE));
	}
	
	@DeleteMapping("exclui-lancamento/{id}")
	public EntityModel<LancamentoModel> excluiLancamento( @PathVariable @Positive Long id ) {
		lancamentoService.excluiLancamento( id );
		return modelAssembler.toModel(new LancamentoModel(id, RequestContext.DELETE));
	}

}
