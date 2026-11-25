package it.trefin.gestioneCinema.factory.concreteProduct;

import it.trefin.gestioneCinema.factory.PaymentMethod;

public class PayPal implements PaymentMethod {

	private boolean checkCredentials() { return true; }
	
	@Override
	public boolean makePayment(double price) {
		return checkCredentials();
	}

}
