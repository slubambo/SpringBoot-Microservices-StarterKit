package com.microservices.templateservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FeignConfiguration {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
			// Get the session-user value from thread-local
			String sessionUser = UserVerificationFilter.getSessionUser();

			if (sessionUser != null) {
				// Add the session-user header to the Feign request
				requestTemplate.header("session-user", sessionUser);
			}

			// Add any other headers or customization you need
		};
	}
}
