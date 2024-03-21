package mis.microservices.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mis.microservices.userservice.payload.auth.UserDetailsResponse;
import mis.microservices.userservice.payload.user.UserDetailsRequest;
import mis.microservices.userservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	/*
	 * Find User Details by Payload Request
	 */
	@PostMapping("/get-user-details")
	public UserDetailsResponse getUserDetails(@Valid @RequestBody UserDetailsRequest request) {
		return userService.getUserDetails(request);
	}

	/*
	 * Find User Details by id path variable
	 */
	@PostMapping("/get-user-details/{id}")
	public UserDetailsResponse getUserDetailsById(@PathVariable Long id) {
		return userService.getUserDetails(id);
	}

	/*
	 * Find User Details by id and user type path variable
	 */
	@PostMapping("/get-user-details/{id}/type/{usertype}")
	public UserDetailsResponse getUserDetailsById(@PathVariable Long id, @PathVariable String usertype) {
		return userService.getUserDetails(id, usertype);
	}

}
