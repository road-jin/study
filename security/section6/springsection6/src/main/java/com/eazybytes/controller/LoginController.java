package com.eazybytes.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.domain.Customer;
import com.eazybytes.domain.CustomerRepository;

@RestController
public class LoginController {

	private final CustomerRepository customerRepository;
	private final PasswordEncoder passwordEncoder;

	public LoginController(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		this.customerRepository = customerRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
		try {
			String hashPwd = passwordEncoder.encode(customer.getPwd());
			customer.setPwd(hashPwd);
			customer.setCreateDt(LocalDate.now().toString());
			customerRepository.save(customer);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("Given user details are successfully registered");
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An exception occured due to " + ex.getMessage());
		}
	}

	@RequestMapping("/user")
	public Customer getUserDetailsAfterLogin(Authentication authentication) {
		return customerRepository.findByEmail(authentication.getName())
			.orElse(null);
	}
}
