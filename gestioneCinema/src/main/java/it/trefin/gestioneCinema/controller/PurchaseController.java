package it.trefin.gestioneCinema.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.trefin.gestioneCinema.model.Ticket;
import it.trefin.gestioneCinema.model.DOM.PurchaseDOM;
import it.trefin.gestioneCinema.service.PurchaseService;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

	private final PurchaseService service;

	public PurchaseController(PurchaseService service) {
		this.service = service;
	}
	
	@PostMapping("/buy")
	Ticket buy(@RequestBody PurchaseDOM dom) {
		return service.buy(dom);
	}
	
}
