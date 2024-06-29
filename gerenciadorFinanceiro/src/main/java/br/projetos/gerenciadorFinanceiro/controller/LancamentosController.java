package br.projetos.gerenciadorFinanceiro.controller;

import java.util.List;

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

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.service.LancamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/lancamento")
@Validated
public class LancamentosController {
	
	private final LancamentoService lancamentoService;
	
	public LancamentosController(LancamentoService lancamentoService) {
		this.lancamentoService = lancamentoService;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public LancamentoDTO incluirLancamento( @RequestBody @Valid LancamentoDTO lancamento ) {
		return lancamentoService.incluirLancamento(lancamento);
	}
	
	@GetMapping
	public List<LancamentoDTO> listaLancamentos(){
		return lancamentoService.listaLancamentos();
	}
	
	@GetMapping("/{id}")
	public LancamentoDTO consultaLancamento( @PathVariable @Positive Long id ) {
		return lancamentoService.consultaLancamento( id );
	}
	
	@PutMapping("/{id}")
	public LancamentoDTO alteraLancamento( @RequestBody @Valid LancamentoDTO lancamento, @PathVariable @Positive Long id ) {
		return lancamentoService.alteraLancamento( id, lancamento );
	}
	
	@DeleteMapping("/{id}")
	public void excluiLancamento( @PathVariable @Positive Long id ) {
		lancamentoService.excluiLancamento( id );
	}

}
