package com.microservices.apigateway.config;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;

@Component
public class RouteValidator {

	public static final List<String> openApiEndpoints = List.of("http://localhost:8765/auth/sign-in");

	public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
			.anyMatch(r -> r.contains(request.getURI().getPath()));
}
