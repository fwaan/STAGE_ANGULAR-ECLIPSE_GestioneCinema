package it.trefin.gestioneCinema.service;

import it.trefin.gestioneCinema.controller.CustomerController;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.stereotype.Service;

import it.trefin.gestioneCinema.exceptions.CustomerNotFoundException;
import it.trefin.gestioneCinema.exceptions.PaymentFailedException;
import it.trefin.gestioneCinema.exceptions.ProjectionNotFoundException;
import it.trefin.gestioneCinema.exceptions.ProjectionSoldOutException;
import it.trefin.gestioneCinema.factory.PaymentMethodManager;
import it.trefin.gestioneCinema.model.Customer;
import it.trefin.gestioneCinema.model.Projection;
import it.trefin.gestioneCinema.model.Ticket;
import it.trefin.gestioneCinema.model.DOM.PurchaseDOM;
import it.trefin.gestioneCinema.model.enums.TicketType;
import it.trefin.gestioneCinema.model.keys.ProjectionKey;
import it.trefin.gestioneCinema.repository.CustomerRepository;
import it.trefin.gestioneCinema.repository.ProjectionRepository;
import it.trefin.gestioneCinema.repository.TicketRepository;
import it.trefin.gestioneCinema.strategy.TicketTypeStrategy;
import it.trefin.gestioneCinema.strategy.concrete.DisabilityTicketType;
import it.trefin.gestioneCinema.strategy.concrete.FastTrackTicketType;
import it.trefin.gestioneCinema.strategy.concrete.OmageTicketType;
import it.trefin.gestioneCinema.strategy.concrete.StandardTicketType;
import it.trefin.gestioneCinema.strategy.concrete.UnderEighteenTicketType;

@Service
public class PurchaseService {

	private static final int FIDELITY_TRESHOLD = 500;
	
	private final TicketRepository ticket_repo;
	private final CustomerRepository customer_repo;
	private final ProjectionRepository projection_repo;
	public PurchaseService(TicketRepository ticket_repo, CustomerRepository customer_repo, ProjectionRepository projection_repo) {
		this.ticket_repo = ticket_repo;
		this.customer_repo = customer_repo;
		this.projection_repo = projection_repo;
	}
	
	public Ticket buy(PurchaseDOM dom) {
		Optional<Customer> u;
		try {
			u = customer_repo.myFindById(dom.getCustomer_id());
			if(u.isEmpty())
				throw new CustomerNotFoundException(dom.getCustomer_id());
		}catch(CustomerNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		Customer unpacked_u = u.get();
		
		
		
		Optional<Projection> p;
		try {
			ProjectionKey pk = dom.getPrj();
			p = projection_repo.myFindByKey(pk.getHall(), pk.getProjection_time());
			if(p.isEmpty())
				throw new ProjectionNotFoundException(pk);
		}catch(ProjectionNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		Projection unpacked_p = p.get();
		
		TicketTypeStrategy strategy;
		TicketType type;
		
		boolean under_eighteen_flag = (ChronoUnit.DAYS.between(LocalDate.now(), unpacked_u.getDob()) < (365 * 18));
		boolean disability_flag = unpacked_u.getDisability();
		boolean fixed_customer_flag = (ticket_repo.countForCustomer(dom.getCustomer_id()) >= FIDELITY_TRESHOLD);
				
		boolean combs[] = {
			under_eighteen_flag & disability_flag & fixed_customer_flag,
			under_eighteen_flag | disability_flag | fixed_customer_flag,
			under_eighteen_flag & disability_flag | fixed_customer_flag,
		};
		
		if(dom.isOmage() || combs[0] || combs[2]) {
			strategy = new OmageTicketType();
			type = TicketType.OMAGE;
		}else if(disability_flag){
			strategy = new DisabilityTicketType();
			type = TicketType.DISABILITY;
		}else if(under_eighteen_flag){
			strategy = new UnderEighteenTicketType();
			type = TicketType.UNDER_18;
		}else if(!combs[1] && dom.isFast_track()) {
			strategy = new FastTrackTicketType();
			type = TicketType.FAST_TRACK;
		}else if(fixed_customer_flag && dom.isFast_track()) {
			strategy = new StandardTicketType();
			type = TicketType.FAST_TRACK;
		}else {
			strategy = new StandardTicketType();
			type = TicketType.STANDARD;
		}
		
		try {
			if(unpacked_p.getCapacity() - unpacked_p.getTickets().size() <= 0)
				throw new ProjectionSoldOutException(unpacked_p);
		}catch(ProjectionSoldOutException e) {
			e.printStackTrace();
			return null;
		}
		
		PaymentMethodManager manager = PaymentMethodManager.selectManager(dom.getPm());
		
		boolean payment_result = manager.pay(strategy.computePrice(unpacked_p.getFilm_id().getStarter_price()));
		
		try {
			if(!payment_result)
				throw new PaymentFailedException();
		}catch(PaymentFailedException e) {
			e.printStackTrace();
			return null;
		}
		
		Ticket bought_ticket = new Ticket(unpacked_p, unpacked_u, type, dom.getPm());
		
		return ticket_repo.save(bought_ticket);
		
	}
	
}
