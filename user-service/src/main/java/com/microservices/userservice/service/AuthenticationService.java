package com.microservices.userservice.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.microservices.userservice.controller.AuthenticationController;
import com.microservices.userservice.model.User;
import com.microservices.userservice.payload.auth.JwtAuthenticationResponse;
import com.microservices.userservice.payload.auth.LoginRequest;
import com.microservices.userservice.payload.general.ApiResponse;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.security.JwtTokenProvider;
import com.microservices.userservice.util.enums.Status;

@Service
public class AuthenticationService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

		logger.info("Signing in and getting token");

		String jwt = null;

		Optional<User> userPick = loginRequest.getUsernameOrEmail() != null && loginRequest.getUsernameOrEmail() != null
				? userRepository.findByUsernameOrEmail(loginRequest.getUsernameOrEmail(),
						loginRequest.getUsernameOrEmail())
				: Optional.empty();

		if (userPick.isPresent()) {

			User user = userPick.get();

			if (user.getStatus() != null && user.getStatus() == 1) {

				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(),
								loginRequest.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);

				jwt = tokenProvider.generateToken(authentication);

				return ResponseEntity.ok(getAuthResponse(authentication, jwt, user));
			} else {
				return new ResponseEntity(new ApiResponse(false, "Your account is not active."), HttpStatus.OK);
			}

		}

		return new ResponseEntity(new ApiResponse(false, "Invalid email or password"), HttpStatus.OK);

	}

	private JwtAuthenticationResponse getAuthResponse(Authentication authentication, String jwt, User user) {

		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);

		var currentUser = userService.getUserDetails(user);

		jwtAuthenticationResponse.setCurrentUser(currentUser);

		return jwtAuthenticationResponse;
	}

}
