package sbnz.integracija.backend.facts;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private MetaRank metaRank;
    @ElementCollection
    private Map<Deck, Double> deckWinrate;
    @ElementCollection
    private Map<Card, Double> centerpieceCardWinrate;
    @ElementCollection
    private Map<Hero, Double> heroWinrate;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DeckCategory> metaDecks;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DeckCategory> counterpickDecks;
    @ManyToMany
    private List<Match> matchHistory;

    @Override
    public String toString() {
        return "Meta{" +
                "id=" + id +
                ", metaRank=" + metaRank +
                ", deckWinrate=" + deckWinrate +
                ", centerpieceCardWinrate=" + centerpieceCardWinrate +
                ", heroWinrate=" + heroWinrate +
                ", metaDecks=" + metaDecks +
                ", counterpickDecks=" + counterpickDecks +
                ", matchHistory=" + matchHistory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meta meta = (Meta) o;
        return Objects.equals(id, meta.id) &&
                metaRank == meta.metaRank &&
                Objects.equals(deckWinrate, meta.deckWinrate) &&
                Objects.equals(centerpieceCardWinrate, meta.centerpieceCardWinrate) &&
                Objects.equals(heroWinrate, meta.heroWinrate) &&
                Objects.equals(metaDecks, meta.metaDecks) &&
                Objects.equals(counterpickDecks, meta.counterpickDecks) &&
                Objects.equals(matchHistory, meta.matchHistory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, metaRank, deckWinrate, centerpieceCardWinrate, heroWinrate, metaDecks, counterpickDecks, matchHistory);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetaRank getMetaRank() {
        return metaRank;
    }

    public void setMetaRank(MetaRank metaRank) {
        this.metaRank = metaRank;
    }

    public Map<Deck, Double> getDeckWinrate() {
        return deckWinrate;
    }

    public void setDeckWinrate(Map<Deck, Double> deckWinrate) {
        this.deckWinrate = deckWinrate;
    }

    public Map<Card, Double> getCenterpieceCardWinrate() {
        return centerpieceCardWinrate;
    }

    public void setCenterpieceCardWinrate(Map<Card, Double> centerpieceCardWinrate) {
        this.centerpieceCardWinrate = centerpieceCardWinrate;
    }

    public Map<Hero, Double> getHeroWinrate() {
        return heroWinrate;
    }

    public void setHeroWinrate(Map<Hero, Double> heroWinrate) {
        this.heroWinrate = heroWinrate;
    }

    public List<DeckCategory> getMetaDecks() {
        return metaDecks;
    }

    public void setMetaDecks(List<DeckCategory> metaDecks) {
        this.metaDecks = metaDecks;
    }

    public List<DeckCategory> getCounterpickDecks() {
        return counterpickDecks;
    }

    public void setCounterpickDecks(List<DeckCategory> counterpickDecks) {
        this.counterpickDecks = counterpickDecks;
    }

    public List<Match> getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(List<Match> matchHistory) {
        this.matchHistory = matchHistory;
    }

    public Meta(Long id, MetaRank metaRank, Map<Deck, Double> deckWinrate, Map<Card, Double> centerpieceCardWinrate, Map<Hero, Double> heroWinrate, List<DeckCategory> metaDecks, List<DeckCategory> counterpickDecks, List<Match> matchHistory) {
        this.id = id;
        this.metaRank = metaRank;
        this.deckWinrate = deckWinrate;
        this.centerpieceCardWinrate = centerpieceCardWinrate;
        this.heroWinrate = heroWinrate;
        this.metaDecks = metaDecks;
        this.counterpickDecks = counterpickDecks;
        this.matchHistory = matchHistory;
    }

    public Meta() {
    }
}
