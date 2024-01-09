package com.eazybytes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.domain.Cards;
import com.eazybytes.domain.CardsRepository;

@RestController
public class CardsController {

	private final CardsRepository cardsRepository;

	public CardsController(CardsRepository cardsRepository) {
		this.cardsRepository = cardsRepository;
	}

	@GetMapping("/myCards")
	public List<Cards> getCardDetails(@RequestParam int id) {
		return cardsRepository.findByCustomerId(id);
	}
}
