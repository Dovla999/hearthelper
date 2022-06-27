package sbnz.integracija.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import sbnz.integracija.backend.dto.MatchHistoryOutputDTO;
import sbnz.integracija.backend.service.CardService;

@RestController
@RequestMapping("/api/cards")
public class CardController {
	private static Logger log = LoggerFactory.getLogger(CardController.class);

	private final CardService cardService;

	@Autowired
	public CardController(CardService cardService) {
		this.cardService = cardService;
	}

	@GetMapping(value = "/match-history", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MatchHistoryOutputDTO> getMatchHistoryForwardChainOutput() {
		MatchHistoryOutputDTO dto;
		try {
			dto = this.cardService.matchHistoryForwardChain();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	@GetMapping(value = "/owned-cards", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MatchHistoryOutputDTO> getOwnedCardsForwardChainOutput() {
		MatchHistoryOutputDTO dto;
		try {
			dto = this.cardService.ownedCardsChainOutput();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	@GetMapping(value = "/heroes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllHeroes() {
		return new ResponseEntity<>(this.cardService.getAllHeroes(), HttpStatus.OK);
	}
	@GetMapping(value = "/deck-categories", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllDeckCategories() {
		return new ResponseEntity<>(this.cardService.getAllDeckCategories(), HttpStatus.OK);
	}
	@GetMapping(value = "/centerpiece-cards", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllCenterpieceCards() {
		return new ResponseEntity<>(this.cardService.getAllCenterpieceCards(), HttpStatus.OK);
	}
	@GetMapping(value = "/meta-ranks", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllMetaRanks() {
		return new ResponseEntity<>(this.cardService.getAllMetaRanks(), HttpStatus.OK);
	}
	@GetMapping(value = "/sample-deck", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getSampleDeck() {
		return new ResponseEntity<>(this.cardService.getSampleDeck(), HttpStatus.OK);
	}

	
}
