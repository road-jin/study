package com.eazybytes.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Accounts, Long> {

	Optional<Accounts> findByCustomerId(int customerId);
}
