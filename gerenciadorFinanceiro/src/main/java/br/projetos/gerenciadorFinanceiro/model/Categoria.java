package br.projetos.gerenciadorFinanceiro.model;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.projetos.gerenciadorFinanceiro.enums.Status;
import br.projetos.gerenciadorFinanceiro.enums.converters.StatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonTypeInfo(
	    use = JsonTypeInfo.Id.NAME,
	    include = JsonTypeInfo.As.PROPERTY,
	    property = "type"
	)
	@JsonSubTypes({
	    @JsonSubTypes.Type(value = Despesa.class, name = "despesa"),
	    @JsonSubTypes.Type(value = Receita.class, name = "receita")
	})
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@Data
@Table(name = "categoria")
@SQLRestriction("status <> 'Inativo'")
@NoArgsConstructor
public abstract class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	@NotNull	
	@Column(length = 20)
	private String nome;
	
	@Column(length = 10)
	@Convert(converter = StatusConverter.class)
	private Status status = Status.ATIVO;
	
	public Categoria(Long id, String nome, Status status) {
	    this.id = id;
		this.nome = nome;
	    this.status = status;
	}
}
