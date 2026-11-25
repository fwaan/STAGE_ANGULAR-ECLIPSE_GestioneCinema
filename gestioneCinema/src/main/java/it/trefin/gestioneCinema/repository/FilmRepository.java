package it.trefin.gestioneCinema.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import it.trefin.gestioneCinema.model.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>{
	
	@Query(value = "SELECT * FROM film", nativeQuery = true)
	public List<Film> getAll();
	
	@Query(value = "SELECT * FROM film WHERE id = :id", nativeQuery = true)
	public Optional<Film> findByIdMine(long id);
	
	@Modifying
	@Query(value = "INSERT INTO film(id, title, genre, duration_in_minutes, starter_price) VALUE (:id, :title, :genre, :duration_in_minutes, :starter_price)", nativeQuery = true)
	public void insert(long id, String title, String genre, int duration_in_minutes, double starter_price);
	
	@Modifying
	@Query(value = "UPDATE film f SET f.id = :id, f.title = :title, f.genre = :genre, f.duration_in_minutes = :duration_in_minutes, f.starter_price = :starter_price WHERE f.id = :id", nativeQuery = true)
	public void modify(long id, String title, String genre, int duration_in_minutes, double starter_price);
	
	@Modifying
	@Query(value = "DELETE FROM film WHERE id = :id", nativeQuery = true)
	public void removeFilm(long id);
	
	public Film findByTitle(String title);

}
