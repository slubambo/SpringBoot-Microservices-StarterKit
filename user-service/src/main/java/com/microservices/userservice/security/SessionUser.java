package com.microservices.userservice.security;

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

	private Collection<?> authorities;

	@Override
	public String toString() {
		return "SessionUser [id=" + id + ", name=" + name + ", username=" + username + ", authorities=" + authorities
				+ "]";
	}

}
