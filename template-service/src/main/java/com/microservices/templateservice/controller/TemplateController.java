package com.microservices.templateservice.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.templateservice.payload.proxy.userservice.UserDetailsRequest;
import com.microservices.templateservice.payload.proxy.userservice.UserDetailsResponse;
import com.microservices.templateservice.proxy.UserServiceProxy;

import jakarta.validation.Valid;

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}

@RestController
@RequestMapping("/template")
public class TemplateController {

	@Autowired
	private UserServiceProxy proxy;

	@Autowired
	private RestTemplate restTemplate;

	/*
	 * Find User Details by Rest Template, however not working because it does not
	 * have an authorization header
	 */

	@GetMapping("/get-user-details-rest/{id}/type/{usertype}")
	public UserDetailsResponse getUserDetailsByRestTemplate(@PathVariable String id, @PathVariable String usertype) {

		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("id", id);
		uriVariables.put("usertype", usertype);

		ResponseEntity<UserDetailsResponse> responseEntity = restTemplate.getForEntity(
				"http://localhost:8765/user/get-user-details/{id}/type/{usertype}", UserDetailsResponse.class,
				uriVariables);

		return responseEntity.getBody();

	}

	/*
	 * Find User Details by Proxy Payload Request
	 */

	@GetMapping("/get-user-details")
	public UserDetailsResponse getUserDetails(@Valid @RequestBody UserDetailsRequest request) {

		UserDetailsResponse userDetails = proxy.retrieveUserDetails(request);

		return userDetails;

	}

	/*
	 * Find User Details by id path variable
	 */

	@GetMapping("/get-user-details/{id}")
	public UserDetailsResponse getUserDetailsById(@PathVariable Long id) {

		UserDetailsResponse userDetails = proxy.getUserDetailsById(id);

		return userDetails;

	}

	/*
	 * Find User Details by id and user type path variable
	 */
	@GetMapping("/get-user-details/{id}/type/{usertype}")
	public UserDetailsResponse getUserDetailsById(@PathVariable Long id, @PathVariable String usertype) {

		UserDetailsResponse userDetails = proxy.getUserDetailsById(id, usertype);

		return userDetails;

	}

}
