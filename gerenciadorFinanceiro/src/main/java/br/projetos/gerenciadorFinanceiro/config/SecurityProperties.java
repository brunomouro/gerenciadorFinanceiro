package br.projetos.gerenciadorFinanceiro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api.security.jwt")
public class SecurityProperties {

	private String secret;
    private long accessExpiration;
    private long refreshExpiration;
    
	public String getSecret() {
		return secret;
	}
	public long getAccessExpiration() {
		return accessExpiration;
	}
	public long getRefreshExpiration() {
		return refreshExpiration;
	}
	
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public void setAccessExpiration(long accessExpiration) {
		this.accessExpiration = accessExpiration;
	}
	
	public void setRefreshExpiration(long refreshExpiration) {
		this.refreshExpiration = refreshExpiration;
	}
}
