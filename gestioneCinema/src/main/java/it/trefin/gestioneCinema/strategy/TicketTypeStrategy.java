package it.trefin.gestioneCinema.strategy;

import it.trefin.gestioneCinema.model.enums.TicketType;

public abstract class TicketTypeStrategy {

	private TicketType format = getType();
	
	abstract protected TicketType getType();
	
	public double computePrice(double price) {
		return price * format.getMult();
	}
}
