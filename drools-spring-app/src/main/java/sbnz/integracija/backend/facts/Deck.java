package sbnz.integracija.backend.facts;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Deck {

    private Long id;
    private HashMap<Card, Integer> cardQuantity;
    private String name;
    private String strategy;
    private DeckCategory category;
    private Hero deckHero;

    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", cardQuantity=" + cardQuantity +
                ", name='" + name + '\'' +
                ", strategy='" + strategy + '\'' +
                ", category=" + category +
                ", deckHero=" + deckHero +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return Objects.equals(id, deck.id) &&
                Objects.equals(cardQuantity, deck.cardQuantity) &&
                Objects.equals(name, deck.name) &&
                Objects.equals(strategy, deck.strategy) &&
                category == deck.category &&
                deckHero == deck.deckHero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardQuantity, name, strategy, category, deckHero);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HashMap<Card, Integer> getCardQuantity() {
        return cardQuantity;
    }

    public void setCardQuantity(HashMap<Card, Integer> cardQuantity) {
        this.cardQuantity = cardQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public DeckCategory getCategory() {
        return category;
    }

    public void setCategory(DeckCategory category) {
        this.category = category;
    }

    public Hero getDeckHero() {
        return deckHero;
    }

    public void setDeckHero(Hero deckHero) {
        this.deckHero = deckHero;
    }

    public Deck(Long id, HashMap<Card, Integer> cardQuantity, String name, String strategy, DeckCategory category, Hero deckHero) {
        this.id = id;
        this.cardQuantity = cardQuantity;
        this.name = name;
        this.strategy = strategy;
        this.category = category;
        this.deckHero = deckHero;
    }

    public Deck() {
    }
}
