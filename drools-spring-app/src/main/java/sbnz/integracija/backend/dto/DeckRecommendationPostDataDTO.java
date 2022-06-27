package sbnz.integracija.backend.dto;

import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.DeckCategory;
import sbnz.integracija.backend.facts.Hero;
import sbnz.integracija.backend.facts.MetaRank;

import java.util.List;

public class DeckRecommendationPostDataDTO {
    public List<Hero> heroes;
    public List<DeckCategory> deck_categories;
    public  List<Integer> centerpiece_cards;
    public MetaRank meta_rank;

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public List<DeckCategory> getDeck_categories() {
        return deck_categories;
    }

    public void setDeck_categories(List<DeckCategory> deck_categories) {
        this.deck_categories = deck_categories;
    }

    public List<Integer> getCenterpiece_cards() {
        return centerpiece_cards;
    }

    public void setCenterpiece_cards(List<Integer> centerpiece_cards) {
        this.centerpiece_cards = centerpiece_cards;
    }

    public MetaRank getMeta_rank() {
        return meta_rank;
    }

    public void setMeta_rank(MetaRank meta_rank) {
        this.meta_rank = meta_rank;
    }

    public DeckRecommendationPostDataDTO(List<Hero> heroes, List<DeckCategory> deck_categories, List<Integer> centerpiece_cards, MetaRank meta_rank) {
        this.heroes = heroes;
        this.deck_categories = deck_categories;
        this.centerpiece_cards = centerpiece_cards;
        this.meta_rank = meta_rank;
    }

    public DeckRecommendationPostDataDTO() {
    }
}
