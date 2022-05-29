package sbnz.integracija.backend.facts;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @ManyToMany
    private List<Match> personalMatchHistory;
    @ManyToMany
    private List<Card> cardsOwned;
    private int dust;
    @ManyToMany
    private List<Deck> decksNotPreferred;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", personalMatchHistory=" + personalMatchHistory +
                ", cardsOwned=" + cardsOwned +
                ", dust=" + dust +
                ", decksNotPreferred=" + decksNotPreferred +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return dust == user.dust &&
                Objects.equals(id, user.id) &&
                Objects.equals(personalMatchHistory, user.personalMatchHistory) &&
                Objects.equals(cardsOwned, user.cardsOwned) &&
                Objects.equals(decksNotPreferred, user.decksNotPreferred);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personalMatchHistory, cardsOwned, dust, decksNotPreferred);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Match> getPersonalMatchHistory() {
        return personalMatchHistory;
    }

    public void setPersonalMatchHistory(List<Match> personalMatchHistory) {
        this.personalMatchHistory = personalMatchHistory;
    }

    public List<Card> getCardsOwned() {
        return cardsOwned;
    }

    public void setCardsOwned(List<Card> cardsOwned) {
        this.cardsOwned = cardsOwned;
    }

    public int getDust() {
        return dust;
    }

    public void setDust(int dust) {
        this.dust = dust;
    }

    public List<Deck> getDecksNotPreferred() {
        return decksNotPreferred;
    }

    public void setDecksNotPreferred(List<Deck> decksNotPreferred) {
        this.decksNotPreferred = decksNotPreferred;
    }

    public User(Long id, List<Match> personalMatchHistory, List<Card> cardsOwned, int dust, List<Deck> decksNotPreferred) {
        this.id = id;
        this.personalMatchHistory = personalMatchHistory;
        this.cardsOwned = cardsOwned;
        this.dust = dust;
        this.decksNotPreferred = decksNotPreferred;
    }

    public User() {
    }
}
