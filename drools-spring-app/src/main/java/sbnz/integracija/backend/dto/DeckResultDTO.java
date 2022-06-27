package sbnz.integracija.backend.dto;

import org.kie.api.definition.type.Position;
import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.Deck;
import sbnz.integracija.backend.facts.DeckCategory;
import sbnz.integracija.backend.facts.Hero;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeckResultDTO {
    private Deck deck;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeckResultDTO that = (DeckResultDTO) o;
        return Objects.equals(deck, that.deck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deck);
    }

    @Override
    public String toString() {
        return "DeckResultDTO{" +
                "deck=" + deck +
                '}';
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public DeckResultDTO(Deck deck) {
        this.deck = deck;
    }

    public DeckResultDTO() {
    }
}
