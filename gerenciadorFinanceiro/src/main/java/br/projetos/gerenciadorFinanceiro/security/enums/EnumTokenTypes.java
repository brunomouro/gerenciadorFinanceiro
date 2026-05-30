package br.projetos.gerenciadorFinanceiro.security.enums;

public enum EnumTokenTypes {
	TOKEN_TYPE_ACCESS("access"),
	TOKEN_TYPE_REFRESH("refresh");
	
	private String tokenType;
	
	EnumTokenTypes(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenType() {
		return tokenType;
	}
}
