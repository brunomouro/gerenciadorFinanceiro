package br.projetos.gerenciadorFinanceiro.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("Receita")
@Data
@EqualsAndHashCode(callSuper = false)
public class Receita extends Categoria {

}
