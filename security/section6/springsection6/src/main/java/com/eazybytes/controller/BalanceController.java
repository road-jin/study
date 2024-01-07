package com.eazybytes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.domain.AccountTransactions;
import com.eazybytes.domain.AccountTransactionsRepository;

@RestController
public class BalanceController {

	private final AccountTransactionsRepository accountTransactionsRepository;

	public BalanceController(AccountTransactionsRepository accountTransactionsRepository) {
		this.accountTransactionsRepository = accountTransactionsRepository;
	}

	@GetMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestParam int id) {
		return accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id);
	}
}
