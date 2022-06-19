package sbnz.integracija.backend.facts;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Map;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class MetaShift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Meta pastMeta;
    @ManyToOne(fetch = FetchType.EAGER)
    private Meta newMeta;
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Map<Deck, Integer> deckRankingChange;

    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Map<Card, Integer> cardRankingChange;
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private Map<Card, Integer> heroRankingChange;
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Enumerated(EnumType.STRING)
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

    public Map<Deck, Integer> getDeckRankingChange() {
        return deckRankingChange;
    }

    public void setDeckRankingChange(Map<Deck, Integer> deckRankingChange) {
        this.deckRankingChange = deckRankingChange;
    }

    public Map<Card, Integer> getCardRankingChange() {
        return cardRankingChange;
    }

    public void setCardRankingChange(Map<Card, Integer> cardRankingChange) {
        this.cardRankingChange = cardRankingChange;
    }

    public Map<Card, Integer> getHeroRankingChange() {
        return heroRankingChange;
    }

    public void setHeroRankingChange(Map<Card, Integer> heroRankingChange) {
        this.heroRankingChange = heroRankingChange;
    }

    public List<DeckCategory> getNewDeckCategories() {
        return newDeckCategories;
    }

    public void setNewDeckCategories(List<DeckCategory> newDeckCategories) {
        this.newDeckCategories = newDeckCategories;
    }

    public MetaShift(Long id, Meta pastMeta, Meta newMeta, Map<Deck, Integer> deckRankingChange, Map<Card, Integer> cardRankingChange, Map<Card, Integer> heroRankingChange, List<DeckCategory> newDeckCategories) {
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
