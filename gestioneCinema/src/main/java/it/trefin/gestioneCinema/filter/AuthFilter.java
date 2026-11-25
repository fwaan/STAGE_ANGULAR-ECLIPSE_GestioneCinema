package it.trefin.gestioneCinema.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import it.trefin.gestioneCinema.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter{

	private final AuthService authService;
	private final UserDetailsService userDetailsService;
	
	public AuthFilter(AuthService authService, UserDetailsService userDetailsService) {
		this.authService = authService;
		this.userDetailsService = userDetailsService;
	}
	
	public UserDetailsService getService() {
		return userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			try {
				username = authService.extractUsername(token);
			} catch (Exception e) {
				username = null;
			}
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			try {
				UserDetails details = userDetailsService.loadUserByUsername(username);
				if(authService.validateToken(token, details)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							details,
							null,
							details.getAuthorities()
					);
					authToken.setDetails(
							new WebAuthenticationDetailsSource().buildDetails(request)
					);
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			} catch (Exception e) {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);
	}
}