package br.projetos.gerenciadorFinanceiro.model;

import br.projetos.gerenciadorFinanceiro.valids.ValidDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@NotBlank
	@ValidDate(message = "Data deve ser no formato dd-MM-yyyy", pattern = "dd/MM/yyyy")
	private String data;
	
	@Positive
	@Column(nullable = false, columnDefinition = "DECIMAL(10, 2)")
	private double valor;
	
	@NotBlank
	@NotNull	
	@Column(length = 100)
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	@ManyToOne
	@JoinColumn(name = "cartao_id")
	private Cartao cartao;

}
