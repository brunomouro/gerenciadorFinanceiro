package br.projetos.gerenciadorFinanceiro.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class DespesaDTOOut extends DespesaDTO{
	private double meta;
	
	public DespesaDTOOut() {
		super();
	}

	public DespesaDTOOut(Long id, String nome, double valorMeta) {
		super(id, nome);
		this.meta = valorMeta;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}
}
