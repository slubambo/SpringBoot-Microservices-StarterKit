package com.microservices.templateservice.payload.proxy.userservice;

import com.microservices.templateservice.util.enums.RoleName;
import com.microservices.templateservice.util.enums.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDetailsResponse {

	private String name;

	private Long id;

	private String username;

	private String email;

	private Integer status;

	private UserType userType;

	private RoleName roleName;

}
