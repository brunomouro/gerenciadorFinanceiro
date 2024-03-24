package br.projetos.gerenciadorFinanceiro;

public class UtilsString {
	
	public static boolean isEmpty(String t) {
		if ( t == null || t.isEmpty() || t.isBlank() ) {
			return true;
		}
		
		return false;
	}

}
