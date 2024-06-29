package br.projetos.gerenciadorFinanceiro.model;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("Despesa")
@Data
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE Categoria SET status = 'Inativo' WHERE id = ?")
public class Despesa extends Categoria {
	
	@Column(columnDefinition = "DECIMAL(10, 2)")
	private Double meta;
}
