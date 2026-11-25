package it.trefin.gestioneCinema.strategy.concrete;

import it.trefin.gestioneCinema.model.enums.TicketType;
import it.trefin.gestioneCinema.strategy.TicketTypeStrategy;

public class OmageTicketType extends TicketTypeStrategy{

	@Override
	protected TicketType getType() {
		return TicketType.OMAGE;
	}
}
