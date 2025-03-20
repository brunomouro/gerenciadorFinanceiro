package br.projetos.gerenciadorFinanceiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.projetos.gerenciadorFinanceiro.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	UserDetails findByLogin(String login);
}
