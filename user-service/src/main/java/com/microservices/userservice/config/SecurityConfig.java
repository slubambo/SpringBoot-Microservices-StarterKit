package com.microservices.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.microservices.userservice.security.CustomUserDetailsService;
import com.microservices.userservice.security.JwtAuthenticationFilter;
import com.microservices.userservice.security.oauth2.CustomOAuth2UserService;
import com.microservices.userservice.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.microservices.userservice.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.microservices.userservice.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.nimbusds.oauth2.sdk.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

	@Autowired
	private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new CustomClientRegistrationRepository();
	}

	private static class CustomClientRegistrationRepository implements ClientRegistrationRepository {

		@Override
		public ClientRegistration findByRegistrationId(String registrationId) {
			// Implement logic to fetch client registration details from your configuration
			// or database
			// This method should return a ClientRegistration object
			return null;
		}
	}

	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeRequests(authorizeRequests -> authorizeRequests
						.requestMatchers(new AntPathRequestMatcher("/auth/**"), new AntPathRequestMatcher("/error"))
						.permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.oauth2Login(oauth2Login -> oauth2Login
						.authorizationEndpoint(
								authorizationEndpoint -> authorizationEndpoint.baseUri("/oauth2/authorize")
										.authorizationRequestRepository(cookieAuthorizationRequestRepository()))
						.redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint.baseUri("/oauth2/callback/*"))
						.userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(customOAuth2UserService))
						.successHandler(oAuth2AuthenticationSuccessHandler)
						.failureHandler(oAuth2AuthenticationFailureHandler))
				.userDetailsService(customUserDetailsService).build();
	}

	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilterRegistration() {
		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtAuthenticationFilter());
		registrationBean.addUrlPatterns("/secured/*"); // Set the URL patterns to which the filter should be applied
		registrationBean.setOrder(1); // Set the desired order for the filter
		return registrationBean;
	}
}
