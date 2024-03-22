package com.microservices.templateservice.config;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
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

}
