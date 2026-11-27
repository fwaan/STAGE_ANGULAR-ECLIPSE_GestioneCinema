package it.trefin.gestioneCinema.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.trefin.gestioneCinema.model.Customer;
import it.trefin.gestioneCinema.model.auth.AuthToken;
import it.trefin.gestioneCinema.repository.CustomerRepository;
import it.trefin.gestioneCinema.service.AuthService;
import it.trefin.gestioneCinema.service.CustomerAuthenticationService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/auth")
public class AuthController {

	public record RegisterRequest(String nome, String cognome, LocalDate dob, Boolean disability, String email, String password) {}

	private final CustomerAuthenticationService customerAuthService;
	private final AuthService authService;
	private final AuthenticationManager manager;
	private final CustomerRepository customerRepo;

	public AuthController(AuthService authService, CustomerAuthenticationService customerAuthService, AuthenticationManager manager, CustomerRepository customerRepo) {
		this.customerRepo = customerRepo;
		this.authService = authService;
		this.customerAuthService = customerAuthService;
		this.manager = manager;
	}

	@PostMapping("/createCredentials")
	@Transactional
	public String addNewUser(@RequestParam String email, @RequestParam String password, @RequestParam Long uid) {
		return customerAuthService.addUser(email, password, uid);
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
		if (request.nome() == null || request.cognome() == null || request.dob() == null
				|| request.email() == null || request.password() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dati mancanti");
		}

		String result = customerAuthService.register(request.nome(), request.cognome(), request.dob(),
				Boolean.TRUE.equals(request.disability()), request.email(), request.password());

		if ("Already Present".equals(result))
			return ResponseEntity.status(HttpStatus.CONFLICT).body(result);

		return ResponseEntity.ok(result);
	}

	@PostMapping("/generateToken")
	public String authenticateAndGetToken(@RequestBody AuthToken request) {
		Authentication authentication = manager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		if(authentication.isAuthenticated()) {
			Long uid = customerRepo.findByCustAuthEmail(request.getUsername()).map(Customer::getId).orElse(null);
			return authService.generateToken(request.getUsername(), authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")), uid);
		
		}
		else
			throw new UsernameNotFoundException("Invalid user request");
	}

}
