package com.eazybytes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loans, Long> {

	List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
