package br.projetos.gerenciadorFinanceiro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.dto.CartaoDTO;
import br.projetos.gerenciadorFinanceiro.dto.mapper.CartaoMapper;
import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.repository.CartaoRepository;

@Service
public class CartaoService {
	
	private final CartaoRepository cartaoRepository;
	
	public CartaoService(CartaoRepository cartaoRepository) {
		this.cartaoRepository = cartaoRepository;
	}

	public CartaoDTO incluirCartao(CartaoDTO cartao) {
		return CartaoMapper.toDTO(cartaoRepository.save(CartaoMapper.toEntity(cartao)));
	}

	public List<CartaoDTO> listaCartoes() {
		List<CartaoDTO> cartoes = cartaoRepository.findAll()
										.stream()
										.map(CartaoMapper::toDTO)
										.toList();
		if( cartoes.isEmpty() ) {
			throw new RecordNotFoundExcepttion();
		}
		return cartoes;
	}

	public CartaoDTO consultaCartao(Long id) {
		return CartaoMapper.toDTO(cartaoRepository.findById(id)
				  					 .orElseThrow(() -> new RecordNotFoundExcepttion(id))); 
	}
	
	public CartaoDTO alteraCartao(Long id, CartaoDTO cartao) {
		return CartaoMapper.toDTO(cartaoRepository.findById(id).map( recordFound -> {recordFound.setNome(cartao.nome());
																					 return cartaoRepository.save(recordFound);})
															   .orElseThrow(() -> new RecordNotFoundExcepttion(id)));
	}

	public void excluiCartao(Long id) {
		cartaoRepository.delete(
				cartaoRepository.findById(id)
				.orElseThrow(()-> new RecordNotFoundExcepttion()));
	}

}
