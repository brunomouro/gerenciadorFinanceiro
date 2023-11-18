package br.projetos.gerenciadorFinanceiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
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
	public Lancamento incluirLancamento( @RequestBody Lancamento lancamento ) {
		return lancamentoService.incluirLancamento(lancamento);
	}

}
