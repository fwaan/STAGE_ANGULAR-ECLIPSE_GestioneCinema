package it.trefin.gestioneCinema.exceptions;

import it.trefin.gestioneCinema.model.Projection;

public class ProjectionSoldOutException extends RuntimeException {
	public ProjectionSoldOutException(Projection p) {
		super("The projection of " + p.getProjection_time() + " in hall " + p.getHall() + " of movie " + p.getFilm_id().getTitle() + " is sold out");
	}
}
