package com.microservices.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.microservices.apigateway.config.RouteValidator;
import com.microservices.apigateway.util.JwtUtil;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

	public AuthFilter() {
		super(Config.class);
	}

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public GatewayFilter apply(Config config) {

		return ((exchange, chain) -> {
			ServerHttpRequest request = null;

			if (!validator.isSecured.test(exchange.getRequest())) {

				// header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

					throw new RuntimeException("missing authorization header");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				if (authHeader != null && authHeader.startsWith("Bearer ")) {

					authHeader = authHeader.substring(7);
				}

				try {

					/*
					 * Validate Token and add custom header
					 */
					if (jwtUtil.validateUserToken(authHeader)) {

						request = exchange.getRequest().mutate()
								.header("session-user", jwtUtil.getUserClaims(authHeader)).build();

					} else {

						throw new RuntimeException("un authorized token");
					}

				} catch (Exception e) {

					throw new RuntimeException("un authorized access");
				}
			}
			return chain.filter(exchange.mutate().request(request).build());
		});
	}

	public static class Config {

	}
}
