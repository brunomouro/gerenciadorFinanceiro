package br.projetos.gerenciadorFinanceiro.security;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.projetos.gerenciadorFinanceiro.config.SecurityProperties;
import br.projetos.gerenciadorFinanceiro.dto.RefreshTokenDTO;
import br.projetos.gerenciadorFinanceiro.dto.TokenDTO;
import br.projetos.gerenciadorFinanceiro.exception.RecordNotFoundExcepttion;
import br.projetos.gerenciadorFinanceiro.model.RefreshToken;
import br.projetos.gerenciadorFinanceiro.model.User;
import br.projetos.gerenciadorFinanceiro.repository.RefreshTokenRepository;
import br.projetos.gerenciadorFinanceiro.repository.UserRepository;
import br.projetos.gerenciadorFinanceiro.security.enums.EnumTokenTypes;
import jakarta.annotation.PostConstruct;

@Service
public class TokenService {
	
	private final SecurityProperties securityProperties;
    
    private RefreshTokenRepository refreshTokenRepository;
    
    private UserRepository userRepository;
    
    private Algorithm algorithm;
    
    public TokenService(RefreshTokenRepository refreshTokenRepository, 
    					UserRepository userRepository,
    					SecurityProperties securityProperties) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.userRepository = userRepository;
		this.securityProperties = securityProperties;
	}

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(this.securityProperties.getSecret());
    }

	public TokenDTO generateTokens(User user){
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);

        return new TokenDTO(accessToken, refreshToken);
    }
    
	private String generateRefreshToken(User user) {
		Instant refreshExpiry = genExpirationDate(EnumTokenTypes.TOKEN_TYPE_REFRESH);
		
		String token;
		
		try {
			 token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(user.getLogin())
					.withClaim("type", EnumTokenTypes.TOKEN_TYPE_REFRESH.getTokenType())
					.withExpiresAt(refreshExpiry)
					.sign(this.algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while generating token", exception);
		}
		
		saveRefreshToken(user, token, refreshExpiry);
		
		return token;
	}
    
    private void saveRefreshToken(User user, String token, Instant refreshExpiry) {
        RefreshToken rt = new RefreshToken();
        rt.setToken(token);
        rt.setUsername(user.getLogin());
        rt.setRevoked(false);
        rt.setExpiryDate(refreshExpiry);

        refreshTokenRepository.save(rt);
    }
    
    private String generateAccessToken(User user) {
    	List<String> roles = user.getAuthorities().stream()
                			 .map(GrantedAuthority::getAuthority)
                			 .toList();
    	
        try{
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDate(EnumTokenTypes.TOKEN_TYPE_ACCESS))
                    .withClaim("type", EnumTokenTypes.TOKEN_TYPE_ACCESS.getTokenType())
                    .withClaim("roles", roles)
                    .sign(this.algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }
    
    private Instant genExpirationDate(EnumTokenTypes tokenType){
        
        if ( tokenType.equals(EnumTokenTypes.TOKEN_TYPE_ACCESS)) {
        	return Instant.now().plusMillis( this.securityProperties.getAccessExpiration() );
        }

        return Instant.now().plusMillis( this.securityProperties.getRefreshExpiration() );
    }
    
    public TokenDTO refreshToken(RefreshTokenDTO refreshTokenDTO) {
    	
    	String oldToken = refreshTokenDTO.refreshToken();

        var storedToken = refreshTokenRepository.findByToken(oldToken)
                								.orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // 🚨 Detect reuse
        if (storedToken.isRevoked()) {
            throw new RuntimeException("Refresh token reuse detected!");
        }
        
        if (storedToken.getExpiryDate().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        // Validate JWT
        DecodedJWT jwt = validateToken(oldToken, 
        							   EnumTokenTypes.TOKEN_TYPE_REFRESH.getTokenType() );

        // Revoke old token
        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        User user = (User)userRepository.findByLogin(jwt.getSubject())
        								.orElseThrow(()-> new RecordNotFoundExcepttion());

        // Generate new tokens
        TokenDTO tokenDTO = generateTokens(user);

        return tokenDTO;
    }
    
    public DecodedJWT validateToken(String token, String expectedType) {
    	DecodedJWT jwt = JWT.require(this.algorithm)
    			.withIssuer("auth-api")
    			.build()
    			.verify(token);
    	
    	if (!expectedType.equals(jwt.getClaim("type").asString())) {
    		throw new RuntimeException("Invalid token type");
    	}
    	
    	return jwt;
    }
}
