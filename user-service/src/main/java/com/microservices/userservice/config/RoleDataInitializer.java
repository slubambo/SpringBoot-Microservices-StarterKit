package com.microservices.userservice.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.microservices.userservice.model.Role;
import com.microservices.userservice.repository.RoleRepository;
import com.microservices.userservice.util.enums.RoleName;

@Component
public class RoleDataInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;

	public RoleDataInitializer(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public void run(String... args) {
		ensureRoleExists(RoleName.ROLE_USER);
		ensureRoleExists(RoleName.ROLE_ADMIN);
	}

	private void ensureRoleExists(RoleName roleName) {
		if (roleRepository.findByName(roleName).isEmpty()) {
			roleRepository.save(new Role(roleName));
		}
	}
}
