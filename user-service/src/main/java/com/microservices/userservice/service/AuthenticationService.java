package com.microservices.userservice.service;

import java.util.Collections;
import java.util.Optional;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.microservices.userservice.controller.AuthenticationController;
import com.microservices.userservice.exception.AppException;
import com.microservices.userservice.model.Role;
import com.microservices.userservice.model.User;
import com.microservices.userservice.payload.auth.JwtAuthenticationResponse;
import com.microservices.userservice.payload.auth.LoginRequest;
import com.microservices.userservice.payload.auth.SignUpRequest;
import com.microservices.userservice.payload.general.ApiResponse;
import com.microservices.userservice.repository.RoleRepository;
import com.microservices.userservice.repository.UserRepository;
import com.microservices.userservice.security.JwtTokenProvider;
import com.microservices.userservice.util.enums.RoleName;
import com.microservices.userservice.util.enums.Status;

import jakarta.validation.Valid;

@Service
public class AuthenticationService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	RoleRepository roleRepository;

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

			if (user.getStatus() != null && user.getStatus().equals(Status.ACTIVE)) {

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

	public ResponseEntity<?> registerUser(@Valid SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));

		user.setRoles(Collections.singleton(userRole));

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}

}
