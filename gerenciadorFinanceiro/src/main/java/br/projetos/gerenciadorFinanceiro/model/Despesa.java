package br.projetos.gerenciadorFinanceiro.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("Despesa")
@Data
@EqualsAndHashCode(callSuper = false)
public class Despesa extends Categoria {
	
	@Positive
	@Column(columnDefinition = "DECIMAL(10, 2)")
	private Double meta;
}
