package it.trefin.gestioneCinema.model.keys;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ProjectionKey implements Serializable{

	private String hall;
	private LocalDateTime projection_time;
	
	public ProjectionKey() {
		super();
	}
	public ProjectionKey(String hall, LocalDateTime projection_time) {
		super();
		this.hall = hall;
		this.projection_time = projection_time;
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
	@Override
	public int hashCode() {
		return Objects.hash(hall, projection_time);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectionKey other = (ProjectionKey) obj;
		return Objects.equals(hall, other.hall) && Objects.equals(projection_time, other.projection_time);
	}
	
	
}
