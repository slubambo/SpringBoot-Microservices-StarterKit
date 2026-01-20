package com.microservices.userservice.payload.auth;

public class JwtAuthenticationResponse {

	private String accessToken;
	private String tokenType = "Bearer";
	private UserDetailsResponse currentUser;

	public JwtAuthenticationResponse(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public UserDetailsResponse getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(UserDetailsResponse currentUser) {
		this.currentUser = currentUser;
	}

}
