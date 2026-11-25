package it.trefin.gestioneCinema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.trefin.gestioneCinema.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
	@Query(value = "SELECT * FROM customer WHERE id = :id", nativeQuery = true)
	Optional<Customer> myFindById(long id);

}