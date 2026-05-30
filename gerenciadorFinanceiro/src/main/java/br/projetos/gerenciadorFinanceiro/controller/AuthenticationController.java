package br.projetos.gerenciadorFinanceiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.projetos.gerenciadorFinanceiro.dto.AuthenticationDTO;
import br.projetos.gerenciadorFinanceiro.dto.RefreshTokenDTO;
import br.projetos.gerenciadorFinanceiro.dto.RegisterDTO;
import br.projetos.gerenciadorFinanceiro.dto.TokenDTO;
import br.projetos.gerenciadorFinanceiro.security.services.AuthenticationService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService service;

	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var tokenDTO = service.login(data);
		
		return ResponseEntity.ok(tokenDTO);
	}
	
	@PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
		service.register(data);

        return ResponseEntity.ok().build();
    }
	
	@PostMapping("/refresh")
	public ResponseEntity<TokenDTO> refresh(@RequestBody RefreshTokenDTO token) {
	    return ResponseEntity.ok(service.refreshToken(token));
	}
}
