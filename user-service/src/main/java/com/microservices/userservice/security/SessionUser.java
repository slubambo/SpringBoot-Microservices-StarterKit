package com.microservices.userservice.security;

import java.util.Collection;

public class SessionUser {

	private Long id;

	private String name;

	private String username;

	private Collection<?> authorities;

	public SessionUser() {
	}

	public SessionUser(Long id, String name, String username, Collection<?> authorities) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.authorities = authorities;
	}

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
