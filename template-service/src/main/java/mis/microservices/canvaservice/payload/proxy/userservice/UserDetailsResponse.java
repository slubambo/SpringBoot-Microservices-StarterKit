package mis.microservices.canvaservice.payload.proxy.userservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mis.microservices.canvaservice.util.enums.RoleName;
import mis.microservices.canvaservice.util.enums.UserType;

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
