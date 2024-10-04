package br.projetos.gerenciadorFinanceiro.controller;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	
	@ExceptionHandler(RecordNotFoundExcepttion.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleRecordNotFoundException(RecordNotFoundExcepttion ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentNotValidExcpetion(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + " " + error.getDefaultMessage())
				.reduce("", (acc, error) -> acc + error + "\n");
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleConstraintViolationException(ConstraintViolationException ex) {
		return ex.getConstraintViolations().stream()
				.map(error -> error.getPropertyPath() + " " + error.getMessage() )
				.reduce("", (acc, error) -> acc + error + "\n");
	}
	
	@ExceptionHandler(SQLException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handlePSQLException(SQLException ex ) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		if (ex != null && ex.getRequiredType() != null) {
			String type = ex.getRequiredType().getName();
			String[] typeParts = type.split("\\.");
			String typeName = typeParts[typeParts.length - 1];
			return ex.getName() + " deve ser do tipo " + typeName;
		}
		return "Tipo do argumento não é valido";
	}
}
