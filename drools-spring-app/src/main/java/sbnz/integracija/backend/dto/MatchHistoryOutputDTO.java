package sbnz.integracija.backend.dto;

import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.DeckCategory;
import sbnz.integracija.backend.facts.Hero;

public class MatchHistoryOutputDTO {

    private Hero hero;
    private DeckCategory deckCategory;
    private Card centerpieceCard;

    public MatchHistoryOutputDTO() {}

    public MatchHistoryOutputDTO(Hero hero, DeckCategory deckCategory, Card centerpieceCard) {
        this.hero = hero;
        this.deckCategory = deckCategory;
        this.centerpieceCard = centerpieceCard;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public DeckCategory getDeckCategory() {
        return deckCategory;
    }

    public void setDeckCategory(DeckCategory deckCategory) {
        this.deckCategory = deckCategory;
    }

    public Card getCenterpieceCard() {
        return centerpieceCard;
    }

    public void setCenterpieceCard(Card centerpieceCard) {
        this.centerpieceCard = centerpieceCard;
    }
}
