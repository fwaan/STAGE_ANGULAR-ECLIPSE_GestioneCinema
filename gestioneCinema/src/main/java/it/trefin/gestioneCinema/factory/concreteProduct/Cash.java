package it.trefin.gestioneCinema.factory.concreteProduct;

import it.trefin.gestioneCinema.factory.PaymentMethod;

public class Cash implements PaymentMethod {

	@Override
	public boolean makePayment(double price) {
		return true;
	}

}
