package it.trefin.gestioneCinema.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.trefin.gestioneCinema.model.Film;
import it.trefin.gestioneCinema.model.Projection;
import it.trefin.gestioneCinema.model.keys.ProjectionKey;

public interface ProjectionRepository extends JpaRepository<Projection, ProjectionKey>{

	
	@Query(value = "SELECT * FROM projection", nativeQuery = true)
	public List<Projection> getAll();
	
	@Query(value = "SELECT * FROM projection WHERE film_id = :id", nativeQuery = true)
	public List<Projection> getProjectionsForMovie(long id);
	
	@Query(value = "SELECT * FROM projection WHERE projection_time >= :date_begin AND projection_time <= :next_date", nativeQuery = true)
	public List<Projection> getProjectionsForData(LocalDateTime date_begin, LocalDateTime next_date);

	@Query(value = "INSERT INTO projection(hall, projection_time, film_id, capacity) VALUES(:hall, :projection_time, :film_id, :capacity)", nativeQuery = true)
	public void insert(String hall, LocalDateTime projection_time, Film film_id, int capacity);
	
	@Query(value = "SELECT * FROM projection WHERE hall = :hall AND projection_time = :projection_time", nativeQuery = true)
	public Optional<Projection> myFindByKey(String hall, LocalDateTime projection_time);
	
	@Query(value = "SELECT p.hall, p.projection_time, p.film_id, p.capacity FROM projection p JOIN film f ON p.film_id = f.id WHERE f.title = :title", nativeQuery = true)
	public List<Projection> findByMovieTitle(String title);

	@Query(value = "SELECT COUNT(*) FROM projection p JOIN ticket t ON p.projection_time = t.projection_id_timestamp AND p.hall = t.projection_id_sala WHERE p.projection_time = :pk_time AND p.hall = :pk_hall", nativeQuery = true)
	public int getOccupiedSeats(LocalDateTime pk_time, String pk_hall);
	
	
}
