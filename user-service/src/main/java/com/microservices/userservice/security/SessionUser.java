package com.microservices.userservice.security;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SessionUser {

	private Long id;

	private String name;

	private String username;

	private Collection<?> authorities;

	@Override
	public String toString() {
		return "SessionUser [id=" + id + ", name=" + name + ", username=" + username + ", authorities=" + authorities
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Collection<?> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<?> authorities) {
		this.authorities = authorities;
	}

}
