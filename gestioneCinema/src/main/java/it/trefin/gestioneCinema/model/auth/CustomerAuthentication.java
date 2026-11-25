package it.trefin.gestioneCinema.model.auth;

import it.trefin.gestioneCinema.model.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class CustomerAuthentication {

	@Id
	private String email;
	
	@Column(nullable = false)
	private String password = "default";
	
	@Column(nullable = false)
	private boolean admin = false;
	
	@OneToOne(mappedBy = "custAuth")
	private Customer customer;

	public String getEmail() {
		return email;
	}

	public void setId(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerAuthentication(String email, String password, boolean admin, Customer customer) {
		super();
		this.email = email;
		this.password = password;
		this.customer = customer;
		this.admin = admin;
	}

	public CustomerAuthentication() {
		super();
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
}
