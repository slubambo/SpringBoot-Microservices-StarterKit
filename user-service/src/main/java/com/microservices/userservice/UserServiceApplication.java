package com.microservices.userservice;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EntityScan(basePackageClasses = { UserServiceApplication.class, Jsr310JpaConverters.class })
//@EnableJpaRepositories(basePackages = "mis.microservices")
public class UserServiceApplication extends SpringBootServletInitializer {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
