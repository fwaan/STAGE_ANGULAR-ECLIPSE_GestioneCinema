package it.trefin.gestioneCinema.exceptions;

public class CustomerNotFoundException extends RuntimeException{

	public CustomerNotFoundException(long id) {
		super("User of id " + id + " not found.");
	}
}
