package br.projetos.gerenciadorFinanceiro.dto.mapper;

import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.dto.LancamentoDTO;
import br.projetos.gerenciadorFinanceiro.model.Lancamento;

@Component
public class LancamentoMapper {
	
    public LancamentoDTO toDTO(Lancamento lancamento) {
        if (lancamento == null) {
            return null;
        }
        return new LancamentoDTO(lancamento.getId(), 
        						 lancamento.getData(),
        						 lancamento.getDescricao(),
        						 lancamento.getValor(), 
        						 lancamento.getCategoria(), 
        						 lancamento.getCartao());
    }

    public Lancamento toEntity(LancamentoDTO lancamentoDTO) {

        if (lancamentoDTO == null) {
            return null;
        }

        Lancamento lancamento = new Lancamento();
        if (lancamentoDTO.id() != null) {
        	lancamento.setId(lancamentoDTO.id());
        }
        
        lancamento.setData(lancamentoDTO.data());
        lancamento.setDescricao(lancamentoDTO.descricao());
        lancamento.setValor(lancamentoDTO.valor());
        lancamento.setCategoria(lancamentoDTO.categoria());
        lancamento.setCartao(lancamentoDTO.cartao());

        return lancamento;
    }

}
