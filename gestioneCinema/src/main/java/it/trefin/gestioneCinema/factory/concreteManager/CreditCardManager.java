package it.trefin.gestioneCinema.factory.concreteManager;

import it.trefin.gestioneCinema.factory.PaymentMethod;
import it.trefin.gestioneCinema.factory.PaymentMethodManager;
import it.trefin.gestioneCinema.factory.concreteProduct.CreditCard;

public class CreditCardManager implements PaymentMethodManager {

	@Override
	public PaymentMethod createMethod() {
		return new CreditCard();
	}

}
