package com.microservices.templateservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.microservices.templateservice.payload.proxy.userservice.UserDetailsRequest;
import com.microservices.templateservice.payload.proxy.userservice.UserDetailsResponse;

@FeignClient(name = "user-service")
public interface UserServiceProxy {

	@PostMapping("/user/get-user-details")
	public UserDetailsResponse retrieveUserDetails(UserDetailsRequest request);

	@PostMapping("/user/get-user-details/{id}")
	public UserDetailsResponse getUserDetailsById(@PathVariable Long id);

	@PostMapping("/user/get-user-details/{id}/type/{usertype}")
	public UserDetailsResponse getUserDetailsById(@PathVariable Long id, @PathVariable String usertype);

}
