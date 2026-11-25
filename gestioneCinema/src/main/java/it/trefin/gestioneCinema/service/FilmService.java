package it.trefin.gestioneCinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.trefin.gestioneCinema.model.Film;
import it.trefin.gestioneCinema.repository.FilmRepository;

@Service
public class FilmService {

	private final FilmRepository repo;
	public FilmService(FilmRepository repo) {
		this.repo = repo;
	}
	
	public List<Film> getAll(){
		return repo.getAll();
	}
	
	public Optional<Film> findById(long id){
		return repo.findByIdMine(id);
	}
	
	public void insert(Film f) {
		repo.insert(f.getId(), f.getTitle(), f.getGenre(), f.getDuration_in_minutes(), f.getStarter_price());
	}
	
	public void modifyFilm(Film f, long id) {
		repo.modify(id, f.getTitle(), f.getGenre(), f.getDuration_in_minutes(), f.getStarter_price());
	}
	
	public void removeFilm(long id) {
		repo.removeFilm(id);
	}
	
	public Film findByTitle(String title) {
		return repo.findByTitle(title);
	}
}
