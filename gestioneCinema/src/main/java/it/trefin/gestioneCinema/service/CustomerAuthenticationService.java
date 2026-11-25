package it.trefin.gestioneCinema.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.trefin.gestioneCinema.model.Customer;
import it.trefin.gestioneCinema.model.auth.CustomerAuthentication;
import it.trefin.gestioneCinema.model.auth.CustomerDetails;
import it.trefin.gestioneCinema.repository.CustomerAuthenticationRepository;
import it.trefin.gestioneCinema.repository.CustomerRepository;

@Service
public class CustomerAuthenticationService implements UserDetailsService{


	private final CustomerAuthenticationRepository repo;
	private final PasswordEncoder encoder;
	private final CustomerRepository repoC;

	public CustomerAuthenticationService(CustomerAuthenticationRepository repo, PasswordEncoder encoder, CustomerRepository repoC) {
		this.repo = repo;
		this.encoder = encoder;
		this.repoC = repoC;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
			CustomerAuthentication user = repo.findByEmail(username)
					.orElseThrow(() -> new UsernameNotFoundException("User not found with email" + username));

			return new CustomerDetails(user);
	}

	public String addUser(String email, String password, Long uid) {
		Optional<Customer> u = repoC.findById(uid);

		if(u.isEmpty())
			return "User not found";

		if(repo.existsById(email))
			return "Already Present";

		Customer costume = u.get();

		CustomerAuthentication info = new CustomerAuthentication(email, encoder.encode(password), false, costume);

		costume.setCustAuth(info);

		repoC.save(costume);
		return "Success";
	}
	
	@Transactional
	public String register(String nome, String cognome, LocalDate dob, boolean disability, String email, String password) {
		if(repo.existsById(email))
			return "Already Present";
		
		Customer customer = new Customer();
		customer.setNome(nome);
		customer.setCognome(cognome);
		customer.setDob(dob);
		customer.setDisability(disability);
		customer.setCustAuth(new CustomerAuthentication(email, encoder.encode(password), false, customer));
		
		repoC.save(customer);
		return "Success";
	}
}
