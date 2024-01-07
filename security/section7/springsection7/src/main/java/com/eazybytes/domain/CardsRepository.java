package com.eazybytes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CardsRepository extends CrudRepository<Cards, Long> {

	List<Cards> findByCustomerId(int customerId);

}
