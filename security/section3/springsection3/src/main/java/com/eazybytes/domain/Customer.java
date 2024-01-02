package com.eazybytes.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String pwd;

	private String role;

	protected Customer() {
	}

	public Customer(Long id, String email, String pwd, String role) {
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPwd() {
		return pwd;
	}

	public String getRole() {
		return role;
	}
}
