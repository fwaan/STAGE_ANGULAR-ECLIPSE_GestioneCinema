package it.trefin.gestioneCinema.model.auth;


public class AuthToken {

	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public AuthToken(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public AuthToken() {
		super();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
