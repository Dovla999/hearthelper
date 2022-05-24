package sbnz.integracija.backend.facts;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MetaShift {

    private Long id;
    private Meta pastMeta;
    private Meta newMeta;
    private HashMap<Deck, Integer> deckRankingChange;
    private HashMap<Card, Integer> cardRankingChange;
    private HashMap<Card, Integer> heroRankingChange;
    private List<DeckCategory> newDeckCategories;

    @Override
    public String toString() {
        return "MetaShift{" +
                "id=" + id +
                ", pastMeta=" + pastMeta +
                ", newMeta=" + newMeta +
                ", deckRankingChange=" + deckRankingChange +
                ", cardRankingChange=" + cardRankingChange +
                ", heroRankingChange=" + heroRankingChange +
                ", newDeckCategories=" + newDeckCategories +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetaShift metaShift = (MetaShift) o;
        return Objects.equals(id, metaShift.id) &&
                Objects.equals(pastMeta, metaShift.pastMeta) &&
                Objects.equals(newMeta, metaShift.newMeta) &&
                Objects.equals(deckRankingChange, metaShift.deckRankingChange) &&
                Objects.equals(cardRankingChange, metaShift.cardRankingChange) &&
                Objects.equals(heroRankingChange, metaShift.heroRankingChange) &&
                Objects.equals(newDeckCategories, metaShift.newDeckCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pastMeta, newMeta, deckRankingChange, cardRankingChange, heroRankingChange, newDeckCategories);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Meta getPastMeta() {
        return pastMeta;
    }

    public void setPastMeta(Meta pastMeta) {
        this.pastMeta = pastMeta;
    }

    public Meta getNewMeta() {
        return newMeta;
    }

    public void setNewMeta(Meta newMeta) {
        this.newMeta = newMeta;
    }

    public HashMap<Deck, Integer> getDeckRankingChange() {
        return deckRankingChange;
    }

    public void setDeckRankingChange(HashMap<Deck, Integer> deckRankingChange) {
        this.deckRankingChange = deckRankingChange;
    }

    public HashMap<Card, Integer> getCardRankingChange() {
        return cardRankingChange;
    }

    public void setCardRankingChange(HashMap<Card, Integer> cardRankingChange) {
        this.cardRankingChange = cardRankingChange;
    }

    public HashMap<Card, Integer> getHeroRankingChange() {
        return heroRankingChange;
    }

    public void setHeroRankingChange(HashMap<Card, Integer> heroRankingChange) {
        this.heroRankingChange = heroRankingChange;
    }

    public List<DeckCategory> getNewDeckCategories() {
        return newDeckCategories;
    }

    public void setNewDeckCategories(List<DeckCategory> newDeckCategories) {
        this.newDeckCategories = newDeckCategories;
    }

    public MetaShift(Long id, Meta pastMeta, Meta newMeta, HashMap<Deck, Integer> deckRankingChange, HashMap<Card, Integer> cardRankingChange, HashMap<Card, Integer> heroRankingChange, List<DeckCategory> newDeckCategories) {
        this.id = id;
        this.pastMeta = pastMeta;
        this.newMeta = newMeta;
        this.deckRankingChange = deckRankingChange;
        this.cardRankingChange = cardRankingChange;
        this.heroRankingChange = heroRankingChange;
        this.newDeckCategories = newDeckCategories;
    }

    public MetaShift() {
    }
}
