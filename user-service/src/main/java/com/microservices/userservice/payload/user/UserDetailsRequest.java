package com.microservices.userservice.payload.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDetailsRequest {

	private Long id;

	private String usernameOrEmail;

}
