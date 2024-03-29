package com.eazybytes.controller;

import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.domain.LoanRepository;
import com.eazybytes.domain.Loans;

@RestController
public class LoansController {

	private final LoanRepository loanRepository;

	public LoansController(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}

	@GetMapping("/myLoans")
	@PostAuthorize("hasRole('ROOT')")
	public List<Loans> getLoanDetails(@RequestParam int id) {
		return loanRepository.findByCustomerIdOrderByStartDtDesc(id);
	}
}

