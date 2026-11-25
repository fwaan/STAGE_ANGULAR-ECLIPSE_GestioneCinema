package it.trefin.gestioneCinema.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.trefin.gestioneCinema.model.keys.ProjectionKey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "projection")
@IdClass(ProjectionKey.class)
public class Projection {

	@Id
	private String hall;
	@Id
	private LocalDateTime projection_time;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "film_id", nullable = false)
	private Film film_id;
	
	@Column(nullable = false)
	private int capacity;
	
	@OneToMany(mappedBy = "projection_id", cascade = CascadeType.ALL, orphanRemoval = false)
	@JsonIgnore
	private List<Ticket> tickets = new ArrayList<>();

	public Projection() {
		super();
	}

	public Projection(String hall, LocalDateTime projection_time, int capacity) {
		super();
		this.hall = hall;
		this.projection_time = projection_time;
		this.capacity = capacity;
	}

	public String getHall() {
		return hall;
	}

	public void setHall(String hall) {
		this.hall = hall;
	}

	public LocalDateTime getProjection_time() {
		return projection_time;
	}

	public void setProjection_time(LocalDateTime projection_time) {
		this.projection_time = projection_time;
	}

	public Film getFilm_id() {
		return film_id;
	}

	public void setFilm_id(Film film_id) {
		this.film_id = film_id;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	
	
	
	
}
