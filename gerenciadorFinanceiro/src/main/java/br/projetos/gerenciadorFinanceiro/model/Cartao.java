package br.projetos.gerenciadorFinanceiro.model;

import org.hibernate.annotations.SQLDelete;

import br.projetos.gerenciadorFinanceiro.enums.Status;
import br.projetos.gerenciadorFinanceiro.enums.converters.StatusConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@SQLDelete(sql = "UPDATE cartao SET status = 'Inativo' WHERE id = ?")
public class Cartao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	@Convert(converter = StatusConverter.class)
	private Status status = Status.ATIVO;

}
