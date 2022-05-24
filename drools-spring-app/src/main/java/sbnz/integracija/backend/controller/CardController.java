package sbnz.integracija.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import sbnz.integracija.backend.service.CardService;

@RestController
public class CardController {
	private static Logger log = LoggerFactory.getLogger(CardController.class);

	private final CardService cardService;

	@Autowired
	public CardController(CardService cardService) {
		this.cardService = cardService;
	}
	
	
	
}
