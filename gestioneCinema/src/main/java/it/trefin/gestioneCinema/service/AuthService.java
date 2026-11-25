package it.trefin.gestioneCinema.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

	public static final String SECRET = "UbiGuTSHahPHhM0vrXURuffcx4myEsrFpQ50qLcHSPl3ZXmITv5QEKd4QswmbVo4";
	public static final long DURATION_TOKEN = 1000*60*30;
	
	
	public String generateToken(String email) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, email);
	}
	
	private String createToken(Map<String, Object> claims, String email) {
		return Jwts.builder()
				.claims(claims)
				.subject(email)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + DURATION_TOKEN))
				.signWith(getSignKey())
				.compact();
	}
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims c = extractAllClaims(token);
		return claimsResolver.apply(c);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith((SecretKey) getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
}
