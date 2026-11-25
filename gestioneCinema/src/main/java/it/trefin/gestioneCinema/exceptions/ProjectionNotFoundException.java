package it.trefin.gestioneCinema.exceptions;

import it.trefin.gestioneCinema.model.keys.ProjectionKey;

public class ProjectionNotFoundException extends RuntimeException{
	
	public ProjectionNotFoundException(ProjectionKey prk) {
		super("Projection in " + prk.getHall() + " @ " + prk.getProjection_time() + " not found");
	}
}
