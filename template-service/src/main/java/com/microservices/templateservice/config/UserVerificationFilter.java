package com.microservices.templateservice.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserVerificationFilter extends OncePerRequestFilter {

	private static final ThreadLocal<String> sessionUserThreadLocal = new ThreadLocal<>();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {

			String userdetails = request.getHeader("session-user");

			ObjectMapper objectMapper = new ObjectMapper();

			SessionUser sessionUser = objectMapper.readValue(userdetails, SessionUser.class);

			if (sessionUser.getId() == null) {
				filterChain.doFilter(request, response);
				return;
			}

			Authentication authentication = new UsernamePasswordAuthenticationToken(sessionUser, null, null);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Store the authentication object in the request attributes
			sessionUserThreadLocal.set(userdetails);

		} catch (Exception e) {
			// TODO: handle exception
		}

		filterChain.doFilter(request, response);

		// Clear the thread-local value after the request is processed
		sessionUserThreadLocal.remove();

	}

	public static String getSessionUser() {
		return sessionUserThreadLocal.get();
	}

}
