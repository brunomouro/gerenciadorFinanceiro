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

import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.model.Receita;
import br.projetos.gerenciadorFinanceiro.service.DespesaService;
import br.projetos.gerenciadorFinanceiro.service.ReceitaService;

@RestController
@RequestMapping("/api/categoria")
@Validated
public class CategoriasController {
	
	private final DespesaService despesaService;
	private final ReceitaService receitaService;
	
	public CategoriasController(DespesaService categoriasService, ReceitaService receitaService) {
		this.despesaService = categoriasService;
		this.receitaService = receitaService;
	}
	
	@PostMapping("/despesa")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Despesa incluirDespesa( @RequestBody Despesa despesa ) {
		return despesaService.incluirDespesa(despesa);
	}
	
	@GetMapping("/despesa")
	public List<Despesa> listaDespesas(){
		return despesaService.listaDespesas();
	}
	
	@GetMapping("/despesa/{id}")
	public Categoria consultaDespesa( @PathVariable Long id ) {
		return despesaService.consultaDespesa( id );
	}
	
	@PutMapping("/despesa/{id}")
	public Categoria alteraDespesa( @RequestBody Despesa despesa, @PathVariable Long id ) {
		return despesaService.alteraDespesa( id, despesa );
	}
	
	@DeleteMapping("/despesa/{id}")
	public void excluiDespesa( @PathVariable Long id ) {
		despesaService.excluiDespesa( id );
	}
	
	@PostMapping("/receita")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Receita incluirReceita( @RequestBody Receita receita ) {
		return receitaService.incluirReceita(receita);
	}
	
	@GetMapping("/receita")
	public List<Receita> listaReceitas(){
		return receitaService.listaReceitas();
	}
	
	@GetMapping("/receita/{id}")
	public Categoria consultaReceita( @PathVariable Long id ) {
		return receitaService.consultaReceita( id );
	}
	
	@PutMapping("/receita/{id}")
	public Categoria alteraReceita( @RequestBody Receita Receita, @PathVariable Long id ) {
		return receitaService.alteraReceita( id, Receita );
	}
	
	@DeleteMapping("/receita/{id}")
	public void excluiCategoria( @PathVariable Long id ) {
		receitaService.excluiReceita( id );
	}

}
