package it.trefin.gestioneCinema.factory.concreteManager;

import it.trefin.gestioneCinema.factory.PaymentMethod;
import it.trefin.gestioneCinema.factory.PaymentMethodManager;
import it.trefin.gestioneCinema.factory.concreteProduct.Cash;

public class CashManager implements PaymentMethodManager {

	@Override
	public PaymentMethod createMethod() {
		return new Cash();
	}

}
