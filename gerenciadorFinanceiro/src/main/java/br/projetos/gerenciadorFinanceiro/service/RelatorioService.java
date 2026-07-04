package br.projetos.gerenciadorFinanceiro.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.dto.FiltroGastosCategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.GastosPorCategoriaDTO;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.model.Lancamento;

@Service
public class RelatorioService {
	
	LancamentoService lancamentoService;
	CategoriaService categoriaService;
	
	public RelatorioService(LancamentoService lancamentoService, CategoriaService categoriaService) {
		this.lancamentoService = lancamentoService;
		this.categoriaService = categoriaService;
	}

	public List<GastosPorCategoriaDTO> getGastosPorCategoria(FiltroGastosCategoriaDTO filtros) {
		List<GastosPorCategoriaDTO> listaGastosPorCategoria = new ArrayList<GastosPorCategoriaDTO>();
		
		List<Categoria> categorias = categoriaService.listaDespesas();
		List<Lancamento> lancamentos = lancamentoService.listaLancamentosPorData(filtros.dataInicial(), filtros.dataFinal());
		
		for (Categoria categoria : categorias) {
			Despesa despesa = (Despesa) categoria;
			
			BigDecimal totalLancamentos = new BigDecimal("0");
			BigDecimal totalDisponivel;
			
			for (Lancamento lancamento : lancamentos) {
				if(lancamento.getCategoria().getId() == despesa.getId()) {
					totalLancamentos.add(lancamento.getValor());
				}
			}
			
			BigDecimal valorMeta = despesa.getMeta() == null ? null : despesa.getMeta();
			
			totalDisponivel = valorMeta.subtract(totalLancamentos);
			
			GastosPorCategoriaDTO dto = new GastosPorCategoriaDTO(despesa.getNome(), totalDisponivel, valorMeta, totalLancamentos);
			listaGastosPorCategoria.add(dto);		    
		}
		
		return listaGastosPorCategoria;
	}

}
