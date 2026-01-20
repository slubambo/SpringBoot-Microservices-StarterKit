package com.microservices.templateservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers(PathPatternRequestMatcher.pathPattern("/staff/auth/**"),
						PathPatternRequestMatcher.pathPattern("/error"),
						PathPatternRequestMatcher.pathPattern("/swagger-ui/**"),
						PathPatternRequestMatcher.pathPattern("/swagger-resources/**"),
						PathPatternRequestMatcher.pathPattern("/configuration/**"),
						PathPatternRequestMatcher.pathPattern("/v3/api-docs/**"),
						PathPatternRequestMatcher.pathPattern("/swagger-ui.html"),
						PathPatternRequestMatcher.pathPattern("/v3/api-docs.yaml"),
						PathPatternRequestMatcher.pathPattern("/swagger/**"),
						PathPatternRequestMatcher.pathPattern("/v3/**"),
						PathPatternRequestMatcher.pathPattern("/canva-api-docs/**")).permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new UserVerificationFilter(), UsernamePasswordAuthenticationFilter.class)
				// .userDetailsService(customUserDetailsService)
				.build();
	}

}
