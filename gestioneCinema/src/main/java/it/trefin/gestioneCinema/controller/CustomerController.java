package it.trefin.gestioneCinema.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.trefin.gestioneCinema.model.Customer;
import it.trefin.gestioneCinema.service.CustomerService;

@RestController
@RequestMapping("/customer")
//@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}
	
	@GetMapping("/all")
	public List<Customer> getAll(){
		return service.getAll();
	}
}
