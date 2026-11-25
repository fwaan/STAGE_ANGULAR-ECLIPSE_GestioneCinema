package it.trefin.gestioneCinema.factory.concreteManager;

import it.trefin.gestioneCinema.factory.PaymentMethod;
import it.trefin.gestioneCinema.factory.PaymentMethodManager;
import it.trefin.gestioneCinema.factory.concreteProduct.DebitCard;

public class DebitCardManager implements PaymentMethodManager {

	@Override
	public PaymentMethod createMethod() {
		return new DebitCard();
	}

}
