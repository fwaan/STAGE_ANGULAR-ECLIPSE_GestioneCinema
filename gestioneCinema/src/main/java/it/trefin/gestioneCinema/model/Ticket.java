package it.trefin.gestioneCinema.model;

import it.trefin.gestioneCinema.model.enums.PaymentMethodE;
import it.trefin.gestioneCinema.model.enums.TicketType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int seat;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumns({
		@JoinColumn(name = "projection_id_sala", nullable = false),
		@JoinColumn(name = "projection_id_timestamp", nullable = false)
	})
	private Projection projection_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer_id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TicketType ticket_type;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentMethodE payment_method;

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public Projection getProjection_id() {
		return projection_id;
	}

	public void setProjection_id(Projection projection_id) {
		this.projection_id = projection_id;
	}

	public Customer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Customer customer_id) {
		this.customer_id = customer_id;
	}

	public TicketType getTicket_type() {
		return ticket_type;
	}

	public void setTicket_type(TicketType ticket_type) {
		this.ticket_type = ticket_type;
	}

	public PaymentMethodE getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(PaymentMethodE payment_method) {
		this.payment_method = payment_method;
	}

	public Ticket() {
		super();
	}

	public Ticket(Projection projection_id, Customer customer_id, TicketType ticket_type,
			PaymentMethodE payment_method) {
		super();
		this.projection_id = projection_id;
		this.customer_id = customer_id;
		this.ticket_type = ticket_type;
		this.payment_method = payment_method;
	}
	
	
	
	
}
