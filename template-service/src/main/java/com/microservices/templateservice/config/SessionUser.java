package com.microservices.templateservice.config;

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

	private String userType;

	private Collection<?> authorities;

	private Collection<?> permissions;

	@Override
	public String toString() {
		return "UserTokenHeader [id=" + id + ", name=" + name + ", username=" + username + ", userType=" + userType
				+ ", authorities=" + authorities + ", permissions=" + permissions + "]";
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Collection<?> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<?> authorities) {
		this.authorities = authorities;
	}

	public Collection<?> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<?> permissions) {
		this.permissions = permissions;
	}

}
