package it.trefin.gestioneCinema.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.trefin.gestioneCinema.model.Customer;
import it.trefin.gestioneCinema.repository.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository repo;
	public CustomerService(CustomerRepository repo) {
		this.repo = repo;
	}
	
	public List<Customer> getAll(){
		return repo.findAll();
	}
	
}
