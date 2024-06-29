package br.projetos.gerenciadorFinanceiro.dto;

public class DespesaDTO extends CategoriaDTO{
	
	private double meta;

	public DespesaDTO() {
		super();
	}

	public DespesaDTO(Long id, String nome, double meta) {
		super(id, nome);
		this.meta = meta;
	}

	public double getMeta() {
		return meta;
	}

	public void setMeta(double meta) {
		this.meta = meta;
	}	

}
