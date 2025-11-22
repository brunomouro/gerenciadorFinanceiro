package br.projetos.gerenciadorFinanceiro.model;

import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.projetos.gerenciadorFinanceiro.enums.UserRole;
import br.projetos.gerenciadorFinanceiro.enums.converters.RoleConverter;
import br.projetos.gerenciadorFinanceiro.enums.converters.StatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NaturalId
	@Column(length = 20)
	private String login;
	
	@NotNull
	private String password;
	
	@NotNull
	@Convert(converter = RoleConverter.class)
	private UserRole role = UserRole.USER;
	
    public User(String login, String password, UserRole role){
        this.login = login;
        this.password = password;
        this.role = role;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if( this.role.equals( UserRole.ADMIN ) ) {
			return List.of( new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER") );
		} else {
			return List.of( new SimpleGrantedAuthority("ROLE_USER") );
		}
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

}
