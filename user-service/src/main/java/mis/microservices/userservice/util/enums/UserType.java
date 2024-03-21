package mis.microservices.userservice.util.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserType {

	STAFF(0), STUDENT(1), PROSPECTIVE_APPLICANT(2), JOB_APPLICANT(3), STUDENT_SPONSOR(4), STUDENT_ALUMNI(5),
	CAFETERIA(6);

	private int value;
	private static Map<Integer, UserType> map = new HashMap<>();

	private UserType(int value) {
		this.value = value;
	}

	static {
		for (UserType userType : UserType.values()) {
			map.put(userType.value, userType);
		}
	}

	public static UserType valueOf(int userType) {
		return (UserType) map.get(userType);
	}

	public int getValue() {
		return value;
	}

}
