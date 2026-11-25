package it.trefin.gestioneCinema.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.trefin.gestioneCinema.model.Ticket;
import it.trefin.gestioneCinema.model.keys.ProjectionKey;
import it.trefin.gestioneCinema.repository.TicketRepository;

@Service
public class TicketService {

	private final TicketRepository repo;
	public TicketService(TicketRepository repo) {
		this.repo = repo;
	}
	
	public List<Ticket> getAllByProjection(ProjectionKey pk){
		return repo.getAllByProjection(pk.getHall(), pk.getProjection_time());
	}
	
	public List<Ticket> findAll(){
		return repo.findAll();
	}
	
}
