package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.DeckCategory;
import sbnz.integracija.backend.facts.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WinsAgainstThisDeckCategoryCountAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class WinsAgainstThisDeckCategoryCountAccumulateFunctionContext implements Externalizable {

        public Map countMap = new HashMap();

        public WinsAgainstThisDeckCategoryCountAccumulateFunctionContext() {

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
        return new WinsAgainstThisDeckCategoryCountAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        WinsAgainstThisDeckCategoryCountAccumulateFunctionContext c = (WinsAgainstThisDeckCategoryCountAccumulateFunctionContext) context;
        c.countMap = new HashMap();
    }

    public void accumulate(Serializable context, Object parameters) {
        WinsAgainstThisDeckCategoryCountAccumulateFunctionContext c = (WinsAgainstThisDeckCategoryCountAccumulateFunctionContext) context;

        Map map = (Map<String, Object>) parameters;
        DeckCategory deckCategoryFirstPlayer = (DeckCategory) map.get("deckCategoryFirstPlayer");
        DeckCategory deckCategorySecondPlayer = (DeckCategory) map.get("deckCategorySecondPlayer");
        Boolean firstWon = (Boolean) map.get("firstWon");
        DeckCategory mostFreqDeckCategory = (DeckCategory) map.get("mostFreqDeckCategory");

        map = c.countMap;

        if (deckCategoryFirstPlayer == mostFreqDeckCategory && !firstWon) {
            if (map.containsKey(deckCategorySecondPlayer)) map.put(deckCategorySecondPlayer, ((Integer)map.get(deckCategorySecondPlayer)) + 1);
            else map.put(deckCategorySecondPlayer, 1);
        } else if (deckCategorySecondPlayer == mostFreqDeckCategory && firstWon) {
            if (map.containsKey(deckCategoryFirstPlayer)) map.put(deckCategoryFirstPlayer, ((Integer)map.get(deckCategoryFirstPlayer)) + 1);
            else map.put(deckCategoryFirstPlayer, 1);
        }
    }

    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((WinsAgainstThisDeckCategoryCountAccumulateFunctionContext) context).countMap;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Map.class;
    }
}
