package br.projetos.gerenciadorFinanceiro.service;

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
			
			double totalLancamentos = 0;
			double totalDisponivel = 0;
			
			for (Lancamento lancamento : lancamentos) {
				if(lancamento.getCategoria().getId() == despesa.getId()) {
					totalLancamentos += lancamento.getValor();
				}
			}
			
			totalDisponivel = despesa.getMeta() - totalLancamentos;
			
			GastosPorCategoriaDTO dto = new GastosPorCategoriaDTO(despesa.getNome(), totalDisponivel, despesa.getMeta());
			listaGastosPorCategoria.add(dto);		    
		}
		
		return listaGastosPorCategoria;
	}

}
