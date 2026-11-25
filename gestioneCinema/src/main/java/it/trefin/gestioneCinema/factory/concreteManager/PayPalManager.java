package it.trefin.gestioneCinema.factory.concreteManager;

import it.trefin.gestioneCinema.factory.PaymentMethod;
import it.trefin.gestioneCinema.factory.PaymentMethodManager;
import it.trefin.gestioneCinema.factory.concreteProduct.PayPal;

public class PayPalManager implements PaymentMethodManager {

	@Override
	public PaymentMethod createMethod() {
		return new PayPal();
	}

}
