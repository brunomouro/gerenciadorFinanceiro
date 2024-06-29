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

import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.service.CategoriaService;

@RestController
@RequestMapping("/api/categoria")
@Validated
public class CategoriasController {

	private final CategoriaService categoriaService;
	
	public CategoriasController(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CategoriaDTO incluirDespesa( @RequestBody CategoriaDTO categoria ) {
		return categoriaService.incluirCategoria( categoria );
	}
	
	@GetMapping
	public List<CategoriaDTO> listaCategorias(){
		return categoriaService.listaCategorias();
	}
	
	@GetMapping("{id}")
	public CategoriaDTO consultaCategoria( @PathVariable Long id ) {
		return categoriaService.consultaCategoria( id );
	}
	
	@DeleteMapping("{id}")
	public void excluiCategoria( @PathVariable Long id ) {
		categoriaService.excluiCategoria( id );
	}
	
	@PutMapping("/despesa/{id}")
	public CategoriaDTO alteraDespesa( @RequestBody CategoriaDTO categoria, @PathVariable Long id ) {
		return categoriaService.alteraCategoria( id, categoria );
	}
}
