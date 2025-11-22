package br.projetos.gerenciadorFinanceiro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracoesSeguranca {
	
    @Autowired
    SecurityFilter securityFilter;
    
    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
    	return http
    		.csrf(csrf -> csrf.disable())
    		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    		.authorizeHttpRequests( req -> {
    			req.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll();
                req.requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll();
                req.requestMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/v3/api-docs/swagger-config",
                        "/v3/api-docs"
                    ).permitAll();
                req.requestMatchers(HttpMethod.POST, "/api/lancamento/inclui-lancamento").hasRole("ADMIN");
    			req.requestMatchers(HttpMethod.GET, "/api/lancamento/lista-lancamentos").hasRole("ADMIN");
    			req.anyRequest().authenticated();
    		})
    		.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
    		.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
