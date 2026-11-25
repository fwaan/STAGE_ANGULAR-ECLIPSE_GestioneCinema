package it.trefin.gestioneCinema.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.trefin.gestioneCinema.model.Film;
import it.trefin.gestioneCinema.service.FilmService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/film")
//@CrossOrigin(origins = "http://localhost:4200")
public class FilmController {

	private final FilmService service;

	public FilmController(FilmService service) {
		this.service = service;
	}
	
	//NON STAMPA DURATION
	@GetMapping("/all")
	public List<Film> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/find/{id}")
	public Film findById(@PathVariable long id) {
		return service.findById(id).orElse(null);
	}
	
	@GetMapping("/byName/{title}")
	public Film findByTitle(@PathVariable String title) {
		return service.findByTitle(title);
	}
	
	@PostMapping("/insert")
	@Transactional
	public void insertFilm(@RequestBody Film f) {
		service.insert(f);
	}
	
	@PutMapping("/modify/{id}")
	@Transactional
	public void modifyFilm(@RequestBody Film f, @PathVariable long id) {
		service.modifyFilm(f, id);
	}

	//Idea del bool
	@DeleteMapping("/delete/{id}")
	@Transactional
	public void removeFilm(@PathVariable long id) {
		service.removeFilm(id);
	}
	
}
