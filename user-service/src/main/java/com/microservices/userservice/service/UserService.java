package mis.microservices.userservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mis.microservices.userservice.model.User;
import mis.microservices.userservice.payload.auth.UserDetailsResponse;
import mis.microservices.userservice.payload.user.UserDetailsRequest;
import mis.microservices.userservice.repository.UserRepository;
import mis.microservices.userservice.util.enums.Status;
import mis.microservices.userservice.util.enums.UserType;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserDetailsResponse getUserDetails(User user) {

		UserDetailsResponse response = new UserDetailsResponse();

		if (user != null) {

			response.setId(user.getId());
//			response.setName(user.getName());
			response.setUsername(user.getUsername());
			response.setEmail(user.getEmail());
			response.setUserType(user.getUserType());
			response.setStatus(user.getStatus());
//			response.setPermissions(user.get().getPermissions().stream().map(UserPermission::getPermission)
//					.collect(Collectors.toList()));
		}

		return response;
	}

	public UserDetailsResponse getUserDetails(UserDetailsRequest request) {

		UserDetailsResponse response = new UserDetailsResponse();

		Optional<User> user = request.getId() != null ? userRepository.findById(request.getId()) : Optional.empty();

		user = !user.isPresent() && request.getUsernameOrEmail() != null
				? userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
				: user;

		if (user.isPresent()) {
			response = getUserDetails(user.get());
		}
		return response;
	}

	public UserDetailsResponse getUserDetails(Long id) {

		UserDetailsResponse response = new UserDetailsResponse();

		Optional<User> user = id != null ? userRepository.findById(id) : Optional.empty();

		if (user.isPresent()) {
			response = getUserDetails(user.get());
		}

		return response;
	}

	public UserDetailsResponse getUserDetails(Long id, String usertype) {

		UserDetailsResponse response = new UserDetailsResponse();

		UserType type = usertype != null ? UserType.valueOf(usertype) : null;

		Optional<User> user = id != null && type != null ? userRepository.findByIdAndUsertype(id, type)
				: Optional.empty();

		if (user.isPresent()) {
			response = getUserDetails(user.get());
		}

		return response;
	}

}
