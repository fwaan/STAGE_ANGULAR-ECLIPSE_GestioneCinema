package it.trefin.gestioneCinema.model.enums;

import jakarta.persistence.EnumeratedValue;

public enum PaymentMethodE {
	CREDIT_CARD("Credit Card"),
	DEBIT_CARD("Debit Card"),
	CASH("Cash"),
	PAYPAL("Pay Pal");
	//GIFT_CARD("Gift Card");
	
	@EnumeratedValue
	String name;
	
	PaymentMethodE(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
