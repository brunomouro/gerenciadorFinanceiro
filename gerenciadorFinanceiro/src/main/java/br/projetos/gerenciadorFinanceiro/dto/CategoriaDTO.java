package br.projetos.gerenciadorFinanceiro.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "type"
	)
	@JsonSubTypes({
	    @JsonSubTypes.Type(value = DespesaDTO.class, name = "despesa"),
	    @JsonSubTypes.Type(value = ReceitaDTO.class, name = "receita")
	})
public abstract class CategoriaDTO {
	
	@Id
	private long id;
	
	@NotBlank
	@NotNull	
	@Column(length = 20)
	private String nome;

	
	public CategoriaDTO() {
	}

	public CategoriaDTO(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
