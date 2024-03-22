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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/staff/auth/**"),
						new AntPathRequestMatcher("/error"), new AntPathRequestMatcher("/swagger-ui/**"),
						new AntPathRequestMatcher("/swagger-resources/**"),
						new AntPathRequestMatcher("/configuration/**"), new AntPathRequestMatcher("/v3/api-docs/**"),
						new AntPathRequestMatcher("/swagger-ui.html"), new AntPathRequestMatcher("/v3/api-docs.yaml"),
						new AntPathRequestMatcher("/swagger/**"), new AntPathRequestMatcher("/v3/**"),
						new AntPathRequestMatcher("/canva-api-docs/**")).permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(new UserVerificationFilter(), UsernamePasswordAuthenticationFilter.class)
				// .userDetailsService(customUserDetailsService)
				.build();
	}

}
