package com.eazybytes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.domain.Accounts;
import com.eazybytes.domain.AccountsRepository;

@RestController
public class AccountController {

	private final AccountsRepository accountsRepository;

	public AccountController(AccountsRepository accountsRepository) {
		this.accountsRepository = accountsRepository;
	}

	@GetMapping("/myAccount")
	public Accounts getAccountDetails(@RequestParam int id) {
		return accountsRepository.findByCustomerId(id)
			.orElse(null);
	}
}
