package it.trefin.gestioneCinema.factory.concreteProduct;

import it.trefin.gestioneCinema.factory.PaymentMethod;

public class DebitCard implements PaymentMethod {

	private boolean checkBalance() { return true; }
	
	@Override
	public boolean makePayment(double price) {
		return checkBalance();
	}

}
