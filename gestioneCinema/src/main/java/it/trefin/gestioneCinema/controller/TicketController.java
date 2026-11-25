package it.trefin.gestioneCinema.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.trefin.gestioneCinema.model.Ticket;
import it.trefin.gestioneCinema.model.keys.ProjectionKey;
import it.trefin.gestioneCinema.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	private final TicketService service;

	public TicketController(TicketService service) {
		this.service = service;
	}
	
	@GetMapping("/getAllByProjection")
	List<Ticket> getAllByProjection(@RequestParam ProjectionKey pk){
		return service.getAllByProjection(pk);
	}
	
	//Debugging
	@GetMapping("/all")
	List<Ticket> getAll(){
		return service.findAll();
	}
	
}
