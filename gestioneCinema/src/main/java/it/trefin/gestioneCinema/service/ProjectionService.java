package it.trefin.gestioneCinema.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.trefin.gestioneCinema.model.Projection;
import it.trefin.gestioneCinema.model.keys.ProjectionKey;
import it.trefin.gestioneCinema.repository.ProjectionRepository;

@Service
public class ProjectionService {

	private final ProjectionRepository repo;
	public ProjectionService(ProjectionRepository repo) {
		this.repo = repo;
	}

	public List<Projection> getAll(){
		return repo.getAll();
	}

	public List<Projection> getProjectionsForMovie(long id) {
		return repo.getProjectionsForMovie(id);
	}

	public List<Projection> getProjectionsForData(LocalDate data){
		return repo.getProjectionsForData(data.atStartOfDay(), data.plusDays(1).atStartOfDay());
	}

	public void insert(Projection p) {
		if (p.getFilm_id() == null || p.getHall() == null || p.getProjection_time() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		if (repo.myFindByKey(p.getHall(), p.getProjection_time()).isPresent())
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		repo.insert(p.getHall(), p.getProjection_time(), p.getFilm_id().getId(), p.getCapacity());
	}

	public void modify(String hall, LocalDateTime projection_time, int capacity) {
		if (repo.myFindByKey(hall, projection_time).isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		if (capacity < repo.getOccupiedSeats(projection_time, hall))
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		repo.modify(hall, projection_time, capacity);
	}

	public void remove(String hall, LocalDateTime projection_time) {
		if (repo.getOccupiedSeats(projection_time, hall) > 0)
			throw new ResponseStatusException(HttpStatus.CONFLICT);
		repo.remove(hall, projection_time);
	}

	public List<Projection> findByMovieTitle(String title){
		return repo.findByMovieTitle(title);
	}

	public Optional<Projection> findById(ProjectionKey pk){
		return repo.findById(pk);
	}

	public int seatsLeft(LocalDateTime projection_time, String hall) {
		Optional<Projection> projection = findById(new ProjectionKey(hall, projection_time));
		if(projection.isEmpty())
			return -1;
		int count = repo.getOccupiedSeats(projection_time, hall);
		return projection.get().getCapacity() - count;
	}
}
