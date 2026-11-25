package it.trefin.gestioneCinema.exceptions;

public class PaymentFailedException extends RuntimeException{

	public PaymentFailedException(){
		super("payment failed");
	}
}
