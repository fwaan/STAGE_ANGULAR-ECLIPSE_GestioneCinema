package it.trefin.gestioneCinema.model.auth;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerDetails implements UserDetails{

	private String email;
	private String password;
	private List<GrantedAuthority> authority;
	
	public CustomerDetails(CustomerAuthentication customerAuth) {
		this.email = customerAuth.getEmail();
		this.password = customerAuth.getPassword();
		this.authority = List.of(customerAuth.isAdmin() ? "ROLE_ADMIN" : "ROLE_CUSTOMER")
							.stream()
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authority;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public @Nullable String getPassword() {
		return password;
	}
	
}
