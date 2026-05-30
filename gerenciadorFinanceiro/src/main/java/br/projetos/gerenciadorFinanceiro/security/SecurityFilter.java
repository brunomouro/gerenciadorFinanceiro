package br.projetos.gerenciadorFinanceiro.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.repository.UserRepository;
import br.projetos.gerenciadorFinanceiro.security.enums.EnumTokenTypes;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
    @Autowired
    TokenService tokenService;
    
    @Autowired
    UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		var token = this.recoverToken(request);
		if(token != null){
			try {
				DecodedJWT jwt = tokenService.validateToken(token, EnumTokenTypes.TOKEN_TYPE_ACCESS.getTokenType());
				
				// Hitting the DB on every request ensures roles and ban status are always fresh.
				// For a high-traffic scenario, this could be optimized with a short-lived cache
				// (e.g. Caffeine/Redis) or a token denylist for instant revocation.
				UserDetails user = userRepository.findByLogin(jwt.getSubject())
												 .orElseThrow(()-> new RecordNotFoundExcepttion());;
				
				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}catch(Exception e) {
				throw new RuntimeException("Error while validating token", e);
			}
		}
		filterChain.doFilter(request, response);
	}
	
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

}
