package br.projetos.gerenciadorFinanceiro.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.projetos.gerenciadorFinanceiro.dto.FiltroGastosCategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.GastosPorCategoriaDTO;
import br.projetos.gerenciadorFinanceiro.service.RelatorioService;

@RestController
@RequestMapping("/api/relatorios")
public class RelatoriosController {
	
	private final RelatorioService relatorio;

	public RelatoriosController(RelatorioService relatorio) {
		this.relatorio = relatorio;
	}
	
	@GetMapping("/gastosPorCategoria")
	public List<GastosPorCategoriaDTO> gastosPorCategoria(@RequestBody FiltroGastosCategoriaDTO filtros){
		return relatorio.getGastosPorCategoria(filtros);
	}

}
