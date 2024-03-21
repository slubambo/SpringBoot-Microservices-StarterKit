package com.microservices.userservice.payload.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class JwtAuthenticationResponse {

	private String accessToken;
	private String tokenType = "Bearer";
	private UserDetailsResponse currentUser;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

}
