package br.projetos.gerenciadorFinanceiro.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.projetos.gerenciadorFinanceiro.controller.modelAssembler.LancamentoModel;
import br.projetos.gerenciadorFinanceiro.controller.modelAssembler.LancamentoModelAssembler;
import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.service.LancamentoService;
import br.projetos.gerenciadorFinanceiro.service.files.FileType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(value = "/api/lancamento",
				produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Validated
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Success", content = @Content(schema = @Schema(implementation = LancamentoDTO.class))),
	    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
	    @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
	    @ApiResponse(responseCode = "403", description = "Proibido", content = @Content),
	    @ApiResponse(responseCode = "404", description = "Não encontrado", content = @Content),
	    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
	})
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
	public PagedModel<EntityModel<LancamentoModel>> listaLancamentos(Pageable pageable, PagedResourcesAssembler<LancamentoModel> assembler){
		Page<LancamentoModel> lancamentos = lancamentoService.listaLancamentos(pageable)
			      											 .map(lancamento -> new LancamentoModel(lancamento, RequestContext.GET)); 

		return assembler.toModel(lancamentos, this.modelAssembler);
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
	
	@PostMapping("importar-lancamentos")
	public CollectionModel<EntityModel<LancamentoModel>> importarLancamentos(@RequestParam("file") MultipartFile file){
		List<EntityModel<LancamentoModel>> lancamentos = lancamentoService.importaLancamentos(file)
														  				  .stream()
														  				  .map(lancamento -> modelAssembler.toModel(new LancamentoModel(lancamento, RequestContext.GET))) 
														  				  .collect(Collectors.toList());
		
		return CollectionModel.of(lancamentos);
	}
	
	@GetMapping("exportar-lancamentos")
    public ResponseEntity<Resource> exportarLancamentos(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestParam("format") String format) {
        Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "id"));
        
        FileType fileType = FileType.valueOf(format.toUpperCase());
        
        String filename = "lancamentos_exported" + fileType.getExtension();

        Resource file = lancamentoService.exportaLancamentos(pageable, fileType);
        
        return ResponseEntity.ok()
            .contentType(fileType.getMediaType())
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"")
            .body(file);
    }
}
