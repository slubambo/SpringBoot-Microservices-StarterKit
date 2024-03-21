package com.microservices.userservice.payload.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String password;

	private Long id;

	private String username;

	private String name;

	private String userType;

	private Collection<? extends GrantedAuthority> authorities;

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginRequest(Long id) {
		this.id = id;
	}
}
