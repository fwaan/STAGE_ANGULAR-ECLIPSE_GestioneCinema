package it.trefin.gestioneCinema.model.enums;

import jakarta.persistence.EnumeratedValue;

public enum TicketType {

	STANDARD("Standard", 1.00),
	FAST_TRACK("Fast Track", 1.25),
	UNDER_18("Under 18", 0.5),
	DISABILITY("Disability", 0.3),
	OMAGE("Omage", 0.0);
	
	@EnumeratedValue
	String type;
	double mult;
	
	TicketType(String type, double mult){
		this.type = type;
		this.mult = mult;
	}
	
	public String getType() {
		return type;
	}
	
	public double getMult() {
		return mult;
	}
	
}
