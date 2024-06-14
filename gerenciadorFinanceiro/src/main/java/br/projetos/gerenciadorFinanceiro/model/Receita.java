package br.projetos.gerenciadorFinanceiro.model;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("Receita")
@Data
@EqualsAndHashCode(callSuper = false)
@SQLDelete(sql = "UPDATE Categoria SET status = 'Inativo' WHERE id = ?")
public class Receita extends Categoria {

}
