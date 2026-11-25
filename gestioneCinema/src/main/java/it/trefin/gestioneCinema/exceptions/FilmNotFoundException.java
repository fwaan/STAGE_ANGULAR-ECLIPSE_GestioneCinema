package it.trefin.gestioneCinema.exceptions;

public class FilmNotFoundException extends RuntimeException{

	public FilmNotFoundException(long id){
		super("film of id " + id + " not found.");
	}
}
