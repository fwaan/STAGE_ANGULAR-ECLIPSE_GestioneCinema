package it.trefin.gestioneCinema.strategy.concrete;

import it.trefin.gestioneCinema.model.enums.TicketType;
import it.trefin.gestioneCinema.strategy.TicketTypeStrategy;

public class UnderEighteenTicketType extends TicketTypeStrategy {

	@Override
	protected TicketType getType() {
		return TicketType.UNDER_18;
	}

}
