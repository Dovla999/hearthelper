package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.Deck;
import sbnz.integracija.backend.facts.DeckCategory;
import sbnz.integracija.backend.facts.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CenterpieceCardTheMostDecksCategoryHeroAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class CenterpieceCardTheMostDecksCategoryHeroAccumulateFunctionContext implements Externalizable {

        public Map countMap = new HashMap();

        public CenterpieceCardTheMostDecksCategoryHeroAccumulateFunctionContext() {

        }

        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(countMap);
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            countMap = (Map) in.readObject();
        }

    }

    public void writeExternal(ObjectOutput out) throws IOException {

    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }

    public Serializable createContext() {
        return new CenterpieceCardTheMostDecksCategoryHeroAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        CenterpieceCardTheMostDecksCategoryHeroAccumulateFunctionContext c = (CenterpieceCardTheMostDecksCategoryHeroAccumulateFunctionContext) context;
        c.countMap = new HashMap();
    }

    public void accumulate(Serializable context, Object parameters) {
        CenterpieceCardTheMostDecksCategoryHeroAccumulateFunctionContext c = (CenterpieceCardTheMostDecksCategoryHeroAccumulateFunctionContext) context;

        Map map = (Map<String, Object>) parameters;
        List<Card> cardsOwned = (List<Card>) map.get("cardsOwned");
        Hero mostFreqHero = (Hero) map.get("mostFreqHero");
        DeckCategory mostFreqCategory = (DeckCategory) map.get("mostFreqCategory");
        Deck deck = (Deck) map.get("deck");


        if(deck.getDeckHero().equals(mostFreqHero) && deck.getCategory().equals(mostFreqCategory)) {
            deck.getCardQuantity().keySet().stream().filter(card -> card.getCenterpiece() && cardsOwned.contains(card)).forEach(card -> {
                if(c.countMap.containsKey(card)){
                    c.countMap.put(card, (Integer)c.countMap.get(card)+1);
                }
                else c.countMap.put(card, 1);
            });
        }



    }


    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((CenterpieceCardTheMostDecksCategoryHeroAccumulateFunctionContext) context).countMap;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Map.class;
    }
}
