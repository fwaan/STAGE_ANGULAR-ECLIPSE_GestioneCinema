package it.trefin.gestioneCinema.factory.concreteProduct;

import it.trefin.gestioneCinema.factory.PaymentMethod;

public class CreditCard implements PaymentMethod{
	
	private static final double LIMIT = 1500.00;
	
	public boolean makePayment(double price) {
		if(price <= LIMIT)
			return true;
		return false;
	}

}
