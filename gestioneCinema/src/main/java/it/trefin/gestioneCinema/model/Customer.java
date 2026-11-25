package it.trefin.gestioneCinema.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.trefin.gestioneCinema.model.auth.CustomerAuthentication;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	@Column(nullable = false)
	private LocalDate dob;
	@Column(nullable = false)
	private Boolean disability = false;
	
	@OneToMany(mappedBy = "customer_id", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Ticket> tickets;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "auth_id", referencedColumnName = "email")
	private CustomerAuthentication custAuth;

	public Customer(String nome, String cognome, LocalDate dob, CustomerAuthentication auth_id) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dob = dob;
		this.custAuth = auth_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public Boolean getDisability() {
		return disability;
	}
	
	public void setDisability(Boolean disability) {
		this.disability = disability;
	}

	public Customer() {
		super();
	}

	public CustomerAuthentication getCustAuth() {
		return custAuth;
	}

	public void setCustAuth(CustomerAuthentication custAuth) {
		this.custAuth = custAuth;
	}
	
	
	
}
