package it.trefin.gestioneCinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.trefin.gestioneCinema.model.Film;
import it.trefin.gestioneCinema.repository.FilmRepository;
import it.trefin.gestioneCinema.repository.ProjectionRepository;

@Service
public class FilmService {

	private final FilmRepository repo;
	private final ProjectionRepository projectionRepo;

	public FilmService(FilmRepository repo, ProjectionRepository projectionRepo) {
		this.repo = repo;
		this.projectionRepo = projectionRepo;
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
		if (!projectionRepo.getProjectionsForMovie(id).isEmpty())
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		repo.removeFilm(id);
	}

	public Film findByTitle(String title) {
		return repo.findByTitle(title);
	}
}
