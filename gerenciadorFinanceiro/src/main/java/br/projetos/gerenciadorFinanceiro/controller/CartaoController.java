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

import br.projetos.gerenciadorFinanceiro.dto.CartaoDTO;
import br.projetos.gerenciadorFinanceiro.service.CartaoService;

@RestController
@RequestMapping("/api/cartao")
public class CartaoController {
	
	private final CartaoService cartaoService;
	
	public CartaoController(CartaoService cartaoService) {
		this.cartaoService = cartaoService;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public CartaoDTO incluirCartao( @RequestBody CartaoDTO cartao ) {
		return cartaoService.incluirCartao( cartao );
	}
	
	@GetMapping
	public List<CartaoDTO> listaCartoes(){
		return cartaoService.listaCartoes();
	}
	
	@GetMapping("{id}")
	public CartaoDTO consultaCategoria( @PathVariable Long id ) {
		return cartaoService.consultaCartao( id );
	}
	
	@DeleteMapping("{id}")
	public void excluiCartao( @PathVariable Long id ) {
		cartaoService.excluiCartao( id );
	}
	
	@PutMapping("/despesa/{id}")
	public CartaoDTO alteraDespesa( @RequestBody CartaoDTO cartao, @PathVariable Long id ) {
		return cartaoService.alteraCartao( id, cartao );
	}

}
