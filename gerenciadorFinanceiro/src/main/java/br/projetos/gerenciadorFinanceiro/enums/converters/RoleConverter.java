package br.projetos.gerenciadorFinanceiro.enums.converters;

import java.util.stream.Stream;

import br.projetos.gerenciadorFinanceiro.enums.UserRole;
import jakarta.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<UserRole, String> {

	@Override
	public String convertToDatabaseColumn(UserRole role) {
		if ( role == null) {
			return null;
		}
		
		return role.getRole();
	}

	@Override
	public UserRole convertToEntityAttribute(String value) {
		if (value == null) {
			return null;
		}
		return Stream.of(UserRole.values())
				.filter(s -> s.getRole().equals(value))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
