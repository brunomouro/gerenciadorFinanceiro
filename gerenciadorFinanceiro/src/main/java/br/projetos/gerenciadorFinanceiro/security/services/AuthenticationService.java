package br.projetos.gerenciadorFinanceiro.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.projetos.gerenciadorFinanceiro.dto.AuthenticationDTO;
import br.projetos.gerenciadorFinanceiro.dto.RefreshTokenDTO;
import br.projetos.gerenciadorFinanceiro.dto.RegisterDTO;
import br.projetos.gerenciadorFinanceiro.dto.TokenDTO;
import br.projetos.gerenciadorFinanceiro.exception.UserAlreadyExistsException;
import br.projetos.gerenciadorFinanceiro.model.User;
import br.projetos.gerenciadorFinanceiro.repository.UserRepository;
import br.projetos.gerenciadorFinanceiro.security.TokenService;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
    @Autowired
    private TokenService tokenService;
    
    public TokenDTO login(AuthenticationDTO data) {
    	UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
    	Authentication auth = this.authenticationManager.authenticate(userNamePassword);

    	return tokenService.generateTokens((User) auth.getPrincipal());
    }
    
    public void register(RegisterDTO data) {
    	if (this.repository.findByLogin(data.login()).isPresent()) throw new UserAlreadyExistsException(data.login());
    	
    	String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());

        this.repository.save(newUser);
    }
    
    public TokenDTO refreshToken(RefreshTokenDTO token) {
    	return tokenService.refreshToken(token);
    }
}
