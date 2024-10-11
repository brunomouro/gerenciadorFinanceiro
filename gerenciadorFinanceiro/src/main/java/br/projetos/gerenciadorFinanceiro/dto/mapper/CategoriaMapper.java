package br.projetos.gerenciadorFinanceiro.dto.mapper;

import org.springframework.stereotype.Component;

import br.projetos.gerenciadorFinanceiro.dto.CategoriaDTO;
import br.projetos.gerenciadorFinanceiro.dto.DespesaDTO;
import br.projetos.gerenciadorFinanceiro.dto.DespesaDTOOut;
import br.projetos.gerenciadorFinanceiro.dto.ReceitaDTO;
import br.projetos.gerenciadorFinanceiro.model.Categoria;
import br.projetos.gerenciadorFinanceiro.model.Despesa;
import br.projetos.gerenciadorFinanceiro.model.Receita;

@Component
public class CategoriaMapper {

	public static CategoriaDTO toDTO(Categoria categoria) {
		if (categoria == null) {
			return null;
		}

		if (categoria instanceof Despesa) {
			return toDTO((Despesa) categoria);
		} else {
			if (categoria instanceof Receita) {
				return toDTO((Receita) categoria);
			}
		}
		
		return null;
	}

	public static DespesaDTO toDTO(Despesa despesa) {
		if (despesa == null) {
			return null;
		}

		return new DespesaDTO(despesa.getId(), despesa.getNome());
	}

	public static ReceitaDTO toDTO(Receita receita) {
		if (receita == null) {
			return null;
		}

		return new ReceitaDTO(
				receita.getId(),
				receita.getNome());
	}

	public static Categoria toEntity(CategoriaDTO dto) {
		if (dto == null) {
			return null;
		}

		if (dto instanceof DespesaDTO) {
			return toEntity((DespesaDTO) dto);
		} else {
			if (dto instanceof ReceitaDTO)
			return toEntity((ReceitaDTO) dto);
		} 

		return null;
	}

	public static Despesa toEntity(DespesaDTO dto) {
		if (dto == null) {
			return null;
		}

		Despesa despesa = new Despesa();
		despesa.setId(dto.getId());
		despesa.setNome(dto.getNome());
		return despesa;
	}

	public static Receita toEntity(ReceitaDTO dto) {
		if (dto == null) {
			return null;
		}

		Receita receita = new Receita();
		receita.setId(dto.getId());
		receita.setNome(dto.getNome());
		return receita;
	}

	public static DespesaDTOOut toDTOOut(Despesa despesa) {
		return new DespesaDTOOut(despesa.getId(), despesa.getNome(), despesa.getMeta());
	}
}

