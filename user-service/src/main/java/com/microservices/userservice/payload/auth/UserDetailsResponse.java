package com.microservices.userservice.payload.auth;

import com.microservices.userservice.util.enums.RoleName;
import com.microservices.userservice.util.enums.Status;

public class UserDetailsResponse {

	private String name;

	private Long id;

	private String username;

	private String email;

	private Status status;

	private RoleName roleName;

	public UserDetailsResponse() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public RoleName getRoleName() {
		return roleName;
	}

	public void setRoleName(RoleName roleName) {
		this.roleName = roleName;
	}

}
