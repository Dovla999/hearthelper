package sbnz.integracija.backend.facts;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Match {

    private Long id;
    private Hero firstPlayer;
    private Hero secondPlayer;
    private List<Card> cardsPlayedFirstPlayer;
    private List<Card> cardsPlayedSecondPlayer;
    private Deck deckFirstPlayer;
    private Deck deckSecondPlayer;
    private Boolean firstPlayerWon;
    private Boolean secondPlayerWon;
    private LocalDateTime datePlayed;

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", firstPlayer=" + firstPlayer +
                ", secondPlayer=" + secondPlayer +
                ", cardsPlayedFirstPlayer=" + cardsPlayedFirstPlayer +
                ", cardsPlayedSecondPlayer=" + cardsPlayedSecondPlayer +
                ", deckFirstPlayer=" + deckFirstPlayer +
                ", deckSecondPlayer=" + deckSecondPlayer +
                ", firstPlayerWon=" + firstPlayerWon +
                ", secondPlayerWon=" + secondPlayerWon +
                ", datePlayed=" + datePlayed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(id, match.id) &&
                firstPlayer == match.firstPlayer &&
                secondPlayer == match.secondPlayer &&
                Objects.equals(cardsPlayedFirstPlayer, match.cardsPlayedFirstPlayer) &&
                Objects.equals(cardsPlayedSecondPlayer, match.cardsPlayedSecondPlayer) &&
                Objects.equals(deckFirstPlayer, match.deckFirstPlayer) &&
                Objects.equals(deckSecondPlayer, match.deckSecondPlayer) &&
                Objects.equals(firstPlayerWon, match.firstPlayerWon) &&
                Objects.equals(secondPlayerWon, match.secondPlayerWon) &&
                Objects.equals(datePlayed, match.datePlayed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstPlayer, secondPlayer, cardsPlayedFirstPlayer, cardsPlayedSecondPlayer, deckFirstPlayer, deckSecondPlayer, firstPlayerWon, secondPlayerWon, datePlayed);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hero getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Hero firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Hero getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Hero secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public List<Card> getCardsPlayedFirstPlayer() {
        return cardsPlayedFirstPlayer;
    }

    public void setCardsPlayedFirstPlayer(List<Card> cardsPlayedFirstPlayer) {
        this.cardsPlayedFirstPlayer = cardsPlayedFirstPlayer;
    }

    public List<Card> getCardsPlayedSecondPlayer() {
        return cardsPlayedSecondPlayer;
    }

    public void setCardsPlayedSecondPlayer(List<Card> cardsPlayedSecondPlayer) {
        this.cardsPlayedSecondPlayer = cardsPlayedSecondPlayer;
    }

    public Deck getDeckFirstPlayer() {
        return deckFirstPlayer;
    }

    public void setDeckFirstPlayer(Deck deckFirstPlayer) {
        this.deckFirstPlayer = deckFirstPlayer;
    }

    public Deck getDeckSecondPlayer() {
        return deckSecondPlayer;
    }

    public void setDeckSecondPlayer(Deck deckSecondPlayer) {
        this.deckSecondPlayer = deckSecondPlayer;
    }

    public Boolean getFirstPlayerWon() {
        return firstPlayerWon;
    }

    public void setFirstPlayerWon(Boolean firstPlayerWon) {
        this.firstPlayerWon = firstPlayerWon;
    }

    public Boolean getSecondPlayerWon() {
        return secondPlayerWon;
    }

    public void setSecondPlayerWon(Boolean secondPlayerWon) {
        this.secondPlayerWon = secondPlayerWon;
    }

    public LocalDateTime getDatePlayed() {
        return datePlayed;
    }

    public void setDatePlayed(LocalDateTime datePlayed) {
        this.datePlayed = datePlayed;
    }

    public Match(Long id, Hero firstPlayer, Hero secondPlayer, List<Card> cardsPlayedFirstPlayer, List<Card> cardsPlayedSecondPlayer, Deck deckFirstPlayer, Deck deckSecondPlayer, Boolean firstPlayerWon, Boolean secondPlayerWon, LocalDateTime datePlayed) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.cardsPlayedFirstPlayer = cardsPlayedFirstPlayer;
        this.cardsPlayedSecondPlayer = cardsPlayedSecondPlayer;
        this.deckFirstPlayer = deckFirstPlayer;
        this.deckSecondPlayer = deckSecondPlayer;
        this.firstPlayerWon = firstPlayerWon;
        this.secondPlayerWon = secondPlayerWon;
        this.datePlayed = datePlayed;
    }

    public Match() {
    }
}
