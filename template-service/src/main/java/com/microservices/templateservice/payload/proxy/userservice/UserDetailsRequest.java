package com.microservices.templateservice.payload.proxy.userservice;

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
