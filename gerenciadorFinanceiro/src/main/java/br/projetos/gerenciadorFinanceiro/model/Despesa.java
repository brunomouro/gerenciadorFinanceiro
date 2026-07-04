package br.projetos.gerenciadorFinanceiro.model;

import java.math.BigDecimal;

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
	private BigDecimal meta;
	
    public Despesa(Long id, String nome, Status status, BigDecimal meta) {
        super(id, nome, status);
        this.meta = meta;
    }
}
