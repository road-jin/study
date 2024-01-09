package com.eazybytes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LoanRepository extends CrudRepository<Loans, Long> {

	//@PreAuthorize("isAuthenticated()")
	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
