package br.projetos.gerenciadorFinanceiro.model;

import org.hibernate.annotations.SQLDelete;

import br.projetos.gerenciadorFinanceiro.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("Despesa")
@Data
@EqualsAndHashCode(callSuper = true)
@SQLDelete(sql = "UPDATE Categoria SET status = 'Inativo' WHERE id = ?")
@NoArgsConstructor
public class Despesa extends Categoria {
	
	@Column(columnDefinition = "DECIMAL(10, 2)")
	private Double meta;
	
    public Despesa(Long id, String nome, Status status, Double meta) {
        super(id, nome, status);
        this.meta = meta;
    }
}
