package com.microservices.templateservice.payload.proxy.userservice;

public class UserDetailsRequest {

	private Long id;

	private String usernameOrEmail;

	public UserDetailsRequest() {
	}

	public UserDetailsRequest(Long id, String usernameOrEmail) {
		this.id = id;
		this.usernameOrEmail = usernameOrEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}
}
