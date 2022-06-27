package sbnz.integracija.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import sbnz.integracija.backend.dto.DeckResultDTO;
import sbnz.integracija.backend.dto.MatchHistoryOutputDTO;
import sbnz.integracija.backend.service.CardService;
import sbnz.integracija.backend.service.DeckService;

@RestController
@RequestMapping("/api/decks")
public class DeckController {
    private static Logger log = LoggerFactory.getLogger(CardController.class);

    private final DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @PostMapping(value = "/recommend-deck", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeckResultDTO> getRecommendedDeck() {
        DeckResultDTO dto;
        try {
            dto = this.deckService.recommendDeckForwardChain(null, null, null, null);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
