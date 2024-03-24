package br.projetos.gerenciadorFinanceiro.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@NotBlank
	@NotNull
	@Column(length = 10)
	private String data;
	
	@Positive
	@Column(nullable = false, columnDefinition = "DECIMAL(10, 2)")
	private double valor;
	
	@NotBlank
	@NotNull	
	@Column(length = 100)
	private String descricao;
	
	@Column(length = 20)
	private String categoria;
	
	@Column(length = 3)
	private int cartao;

}
