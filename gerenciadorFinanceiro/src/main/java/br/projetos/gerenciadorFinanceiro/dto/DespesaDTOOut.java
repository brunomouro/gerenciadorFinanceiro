package br.projetos.gerenciadorFinanceiro.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class DespesaDTOOut extends DespesaDTO{
	private BigDecimal meta;
	
	public DespesaDTOOut() {
		super();
	}

	public DespesaDTOOut(Long id, String nome, BigDecimal valorMeta) {
		super(id, nome);
		this.meta = valorMeta;
	}

	public BigDecimal getMeta() {
		return meta;
	}

	public void setMeta(BigDecimal meta) {
		this.meta = meta;
	}
}
