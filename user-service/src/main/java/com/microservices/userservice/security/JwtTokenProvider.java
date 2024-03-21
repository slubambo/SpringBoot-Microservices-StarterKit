package com.microservices.userservice.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.userservice.payload.auth.LoginRequest;

@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	public String generateToken(Authentication authentication) {

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		SessionUser sessionUser = new SessionUser(userPrincipal.getId(), userPrincipal.getName(),
				userPrincipal.getUsername(), userPrincipal.getAuthorities());

		ObjectMapper objectMapper = new ObjectMapper();
		String mapped = null;

		try {
			mapped = objectMapper.writeValueAsString(sessionUser);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> claims = new HashMap<>();
		claims.put("session-user", mapped != null ? mapped : sessionUser.toString());
		return Jwts.builder().setSubject(Long.toString(userPrincipal.getId())).setClaims(claims).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String generateToken(LoginRequest loginRequest) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		SessionUser sessionUser = new SessionUser(loginRequest.getId(), loginRequest.getName(),
				loginRequest.getUsername(), loginRequest.getAuthorities());

		ObjectMapper objectMapper = new ObjectMapper();
		String mapped = null;

		try {
			mapped = objectMapper.writeValueAsString(sessionUser);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> claims = new HashMap<>();
		claims.put("session-user", mapped != null ? mapped : sessionUser.toString());
		return Jwts.builder().setSubject(Long.toString(loginRequest.getId())).setClaims(claims).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
