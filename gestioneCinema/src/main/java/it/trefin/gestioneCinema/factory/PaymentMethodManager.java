package it.trefin.gestioneCinema.factory;

import it.trefin.gestioneCinema.factory.concreteManager.*;
import it.trefin.gestioneCinema.model.enums.PaymentMethodE;

public interface PaymentMethodManager {

	PaymentMethod createMethod();
	default boolean pay(double price) {
		return createMethod().makePayment(price);
	}
	
	static PaymentMethodManager selectManager(PaymentMethodE pme) {
		switch(pme) {
			case PAYPAL:
				return new PayPalManager();
			case CREDIT_CARD:
				return new CreditCardManager();
			case DEBIT_CARD:
				return new DebitCardManager();
			default:
				break;
		};
		return new CashManager();
	}
}
