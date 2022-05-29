package sbnz.integracija.backend.facts;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int atk;
    private int def;
    private int cost;
    private String description;

    @Enumerated(EnumType.STRING)
    private Hero cardClass;
    private String name;
    private Boolean isCenterpiece;
    private int craftingCost;

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", atk=" + atk +
                ", def=" + def +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", cardClass=" + cardClass +
                ", name='" + name + '\'' +
                ", isCenterpiece=" + isCenterpiece +
                ", craftingCost=" + craftingCost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return atk == card.atk &&
                def == card.def &&
                cost == card.cost &&
                craftingCost == card.craftingCost &&
                Objects.equals(id, card.id) &&
                Objects.equals(description, card.description) &&
                cardClass == card.cardClass &&
                Objects.equals(name, card.name) &&
                Objects.equals(isCenterpiece, card.isCenterpiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, atk, def, cost, description, cardClass, name, isCenterpiece, craftingCost);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Hero getCardClass() {
        return cardClass;
    }

    public void setCardClass(Hero cardClass) {
        this.cardClass = cardClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCenterpiece() {
        return isCenterpiece;
    }

    public void setCenterpiece(Boolean centerpiece) {
        isCenterpiece = centerpiece;
    }

    public int getCraftingCost() {
        return craftingCost;
    }

    public void setCraftingCost(int craftingCost) {
        this.craftingCost = craftingCost;
    }

    public Card(Long id, int atk, int def, int cost, String description, Hero cardClass, String name, Boolean isCenterpiece, int craftingCost) {
        this.id = id;
        this.atk = atk;
        this.def = def;
        this.cost = cost;
        this.description = description;
        this.cardClass = cardClass;
        this.name = name;
        this.isCenterpiece = isCenterpiece;
        this.craftingCost = craftingCost;
    }

    public Card() {
    }
}
