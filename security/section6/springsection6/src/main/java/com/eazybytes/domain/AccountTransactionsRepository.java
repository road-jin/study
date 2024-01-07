package com.eazybytes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AccountTransactionsRepository extends CrudRepository<AccountTransactions, Long> {

	List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(int customerId);
}
