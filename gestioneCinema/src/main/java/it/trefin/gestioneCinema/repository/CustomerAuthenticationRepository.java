package it.trefin.gestioneCinema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.trefin.gestioneCinema.model.auth.CustomerAuthentication;


public interface CustomerAuthenticationRepository extends JpaRepository<CustomerAuthentication, String>{
	Optional<CustomerAuthentication> findByEmail(String email);
}
