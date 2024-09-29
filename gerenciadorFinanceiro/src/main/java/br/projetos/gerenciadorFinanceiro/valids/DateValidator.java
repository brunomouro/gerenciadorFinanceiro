package br.projetos.gerenciadorFinanceiro.valids;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

	private String datePattern;

	@Override
	public void initialize(ValidDate constraintAnnotation) {
		this.datePattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String dateStr, ConstraintValidatorContext context) {
        if (dateStr == null) {
            return true; // null values are valid, use @NotNull for null checks.
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
            LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            return false; // Invalid date format
        }

        return true; // Valid date
	}

}
