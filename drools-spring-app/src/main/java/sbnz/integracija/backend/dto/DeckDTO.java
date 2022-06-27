package sbnz.integracija.backend.dto;

import sbnz.integracija.backend.facts.Deck;

import java.util.HashMap;
import java.util.Map;

public class DeckDTO {
    private Map<String, Integer> cards;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getCards() {
        return cards;
    }

    public DeckDTO(Map<String, Integer> cards, String name) {
        this.cards = cards;
        this.name = name;
    }

    public void setCards(Map<String, Integer> cards) {
        this.cards = cards;
    }

    public DeckDTO() {
    }

    public DeckDTO(Map<String, Integer> cards) {
        this.cards = cards;
    }

    public DeckDTO(Deck deck){
        this.cards = new HashMap<>();
        deck.getCardQuantity().keySet().forEach(card -> this.cards.put(card.getName(),deck.getCardQuantity().get(card)));
        this.name = deck.getName();
    }
}
