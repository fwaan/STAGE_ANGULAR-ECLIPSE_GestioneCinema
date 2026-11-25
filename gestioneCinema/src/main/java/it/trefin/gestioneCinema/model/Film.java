package it.trefin.gestioneCinema.model;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "film")
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private int duration_in_minutes;
	@Column(nullable = false)
	private String genre;
	@Column(nullable = false)
	private double starter_price;

	@OneToMany(mappedBy = "film_id", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Projection> projections = new ArrayList<>();

	public Film() {
		super();
	}

	public Film(String title, String genre, int duration_in_minutes, double starter_price) {
		super();
		this.title = title;
		this.genre = genre;
		this.duration_in_minutes = duration_in_minutes;
		this.starter_price = starter_price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	
	

	public int getDuration_in_minutes() {
		return duration_in_minutes;
	}

	public void setDuration_in_minutes(int duration_in_minutes) {
		this.duration_in_minutes = duration_in_minutes;
	}

	public double getStarter_price() {
		return starter_price;
	}

	public void setStarter_price(double starter_price) {
		this.starter_price = starter_price;
	}

	public List<Projection> getProjections() {
		return projections;
	}

	public void setProjections(List<Projection> projections) {
		this.projections = projections;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
