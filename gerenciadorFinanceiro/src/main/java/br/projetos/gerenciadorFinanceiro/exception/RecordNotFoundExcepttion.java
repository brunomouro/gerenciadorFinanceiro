package br.projetos.gerenciadorFinanceiro.exception;

public class RecordNotFoundExcepttion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundExcepttion(Long id) {
		super("Registro nao encontrado com o id: " + id);
	}
	
	public RecordNotFoundExcepttion() {
		super("Registros nao encontrados");
	}
	
	public RecordNotFoundExcepttion(String campo, Long id) {
		super("Nao existe " + campo + " com o id: " + id);
	}

	
}
