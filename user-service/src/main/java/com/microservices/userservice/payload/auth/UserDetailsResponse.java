package mis.microservices.userservice.payload.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mis.microservices.userservice.util.enums.RoleName;
import mis.microservices.userservice.util.enums.Status;
import mis.microservices.userservice.util.enums.UserType;

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
