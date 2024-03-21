package com.microservices.userservice.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.NaturalId;

import com.microservices.userservice.audit.DateAudit;
import com.microservices.userservice.util.enums.Status;
import com.microservices.userservice.util.enums.UserType;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User extends DateAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String username;

	@NaturalId
	@NotBlank
	@Size(max = 60)
	@Email
	private String email;

	@Size(max = 40)
	@Email
	private String alternativeEmail;

	private String phoneNumber;

	@NotBlank
	@Size(max = 100)
	private String password;

	private Integer status;

	private UserType usertype;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UserType getUserType() {
		return usertype;
	}

	public void setUserType(UserType userType) {
		this.usertype = userType;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
