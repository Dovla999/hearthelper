package sbnz.integracija.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sbnz.integracija.backend.dto.DeckDTO;
import sbnz.integracija.backend.dto.DeckRecommendationPostDataDTO;
import sbnz.integracija.backend.dto.DeckResultDTO;
import sbnz.integracija.backend.dto.MatchHistoryOutputDTO;
import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.service.CardService;
import sbnz.integracija.backend.service.DeckService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cards/decks")
public class DeckController {
    private static Logger log = LoggerFactory.getLogger(CardController.class);

    private final DeckService deckService;

    @Autowired
    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    @PostMapping(value = "/recommend-deck", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeckDTO> getRecommendedDeck(@RequestBody DeckRecommendationPostDataDTO deckRecommendationPostDataDTO) {
        DeckResultDTO dto;
        try {
            dto = this.deckService.recommendDeckForwardChain(deckRecommendationPostDataDTO.getMeta_rank(), deckRecommendationPostDataDTO.getDeck_categories(), deckRecommendationPostDataDTO.getHeroes(), deckRecommendationPostDataDTO.getCenterpiece_cards());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
        DeckDTO deckDTO = new DeckDTO();
        deckDTO.setCards(new HashMap<>());
        for(Map.Entry<Card, Integer> cardQuant:dto.getDeck().getCardQuantity().entrySet()){
            deckDTO.getCards().put(cardQuant.getKey().getName(), cardQuant.getValue());
        }
        deckDTO.setName(dto.getDeck().getName());
        return new ResponseEntity<>(deckDTO, HttpStatus.OK);
    }
}
