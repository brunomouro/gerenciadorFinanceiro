package br.projetos.gerenciadorFinanceiro.exception;

public class UserAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException(String user) {
		super("User " + user + " already exists");
	}
}
