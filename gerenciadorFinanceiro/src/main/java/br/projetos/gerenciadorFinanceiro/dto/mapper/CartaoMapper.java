package br.projetos.gerenciadorFinanceiro.dto.mapper;

import br.projetos.gerenciadorFinanceiro.dto.CartaoDTO;
import br.projetos.gerenciadorFinanceiro.model.Cartao;

public class CartaoMapper {
	
	public static CartaoDTO toDTO(Cartao cartao) {
		if (cartao == null) {
			return null;
		}

		return new CartaoDTO(cartao.getId(), cartao.getNome());
	}
	
	public static Cartao toEntity(CartaoDTO dto) {
		if (dto == null) {
			return null;
		}

		Cartao cartao = new Cartao();
		cartao.setId(dto.id());
		cartao.setNome(dto.nome());
		return cartao;
	}

}
