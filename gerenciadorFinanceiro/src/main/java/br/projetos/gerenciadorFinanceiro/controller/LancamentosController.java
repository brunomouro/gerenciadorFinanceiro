package br.projetos.gerenciadorFinanceiro.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.projetos.gerenciadorFinanceiro.model.Lancamento;
import br.projetos.gerenciadorFinanceiro.service.LancamentoService;

@RestController
@RequestMapping("/api/lancamento")
public class LancamentosController {
	
	private final LancamentoService lancamentoService;
	
	public LancamentosController(LancamentoService lancamentoService) {
		this.lancamentoService = lancamentoService;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Lancamento incluirLancamento( @RequestBody Lancamento lancamento ) {
		return lancamentoService.incluirLancamento(lancamento);
	}
	
	@GetMapping
	public List<Lancamento> listaLancamentos(){
		return lancamentoService.listaLancamentos();
	}
	
	@GetMapping("/{id}")
	public Lancamento consultaLancamento( @PathVariable Long id ) {
		return lancamentoService.consultaLancamento( id );
	}
	
	@PutMapping("/{id}")
	public Lancamento alteraLancamento( @RequestBody Lancamento lancamento, @PathVariable Long id ) {
		return lancamentoService.alteraLancamento( id, lancamento );
	}
	
	@DeleteMapping("/{id}")
	public void excluiLancamento( @PathVariable Long id ) {
		lancamentoService.excluiLancamento( id );
	}

}
