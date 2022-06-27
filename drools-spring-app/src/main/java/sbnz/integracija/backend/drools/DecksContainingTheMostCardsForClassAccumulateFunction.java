package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.Deck;
import sbnz.integracija.backend.facts.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DecksContainingTheMostCardsForClassAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class DecksContainingTheMostCardsForClassAccumulateFunctionContext implements Externalizable {

        public Map countMap = new HashMap();

        public DecksContainingTheMostCardsForClassAccumulateFunctionContext() {

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
        return new DecksContainingTheMostCardsForClassAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        DecksContainingTheMostCardsForClassAccumulateFunctionContext c = (DecksContainingTheMostCardsForClassAccumulateFunctionContext) context;
        c.countMap = new HashMap();
    }

    public void accumulate(Serializable context, Object parameters) {
        DecksContainingTheMostCardsForClassAccumulateFunctionContext c = (DecksContainingTheMostCardsForClassAccumulateFunctionContext) context;

        Map map = (Map<String, Object>) parameters;;
        List<Card> cardsOwned = (List<Card>) map.get("cardsOwned");
        Hero mostFreqHero = (Hero) map.get("mostFreqHero");
        Deck deck = (Deck) map.get("deck");

        int cardIntersectionCount = 0;
        if(deck.getDeckHero().equals(mostFreqHero)) {
            cardIntersectionCount = (int) cardsOwned.stream().filter(card -> deck.getCardQuantity().containsKey(card)).count();
        }

        if(c.countMap.containsKey(deck.getCategory())){
            c.countMap.put(deck.getCategory(),(Integer) c.countMap.get(deck.getCategory())+cardIntersectionCount);
        }
        else{
            c.countMap.put(deck.getCategory(), 0);
        }

    }


    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((DecksContainingTheMostCardsForClassAccumulateFunctionContext) context).countMap;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Map.class;
    }
}
