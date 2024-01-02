package com.eazybytes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.domain.Customer;
import com.eazybytes.domain.CustomerRepository;

@RestController
public class LoginController {

	private final CustomerRepository customerRepository;

	public LoginController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
		try {
			customerRepository.save(customer);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Given user details are successfully registered");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An exception occured due to " + ex.getMessage());
		}
	}
}
