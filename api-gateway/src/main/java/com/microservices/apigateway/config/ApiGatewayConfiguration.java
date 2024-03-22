package com.microservices.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservices.apigateway.filter.AuthFilter;

@Configuration
public class ApiGatewayConfiguration {

	@Autowired
	AuthFilter authFilter;

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()

				/*
				 * Routes for Authentication
				 */

				.route(p -> p.path("/auth/**").filters(f -> f.rewritePath("/auth/(?<segment>.*)", "/auth/${segment}"))
						.uri("lb://user-service"))

				/*
				 * Routes for User Service
				 */

				.route(p -> p.path("/user/**").filters(f -> f.filter(authFilter.apply(new AuthFilter.Config())))
						.uri("lb://user-service"))

				/*
				 * Routes for Template Service
				 */

				.route(p -> p.path("/template/**").filters(f -> f.filter(authFilter.apply(new AuthFilter.Config())))
						.uri("lb://template-service"))

				.build();
	}

}
