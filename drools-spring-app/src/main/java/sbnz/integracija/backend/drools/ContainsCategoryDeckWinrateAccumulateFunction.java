package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.Deck;
import sbnz.integracija.backend.facts.DeckCategory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ContainsCategoryDeckWinrateAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class ContainsCategoryDeckWinrateAccumulateFunctionContext implements Externalizable {

        public Map countMap = new HashMap();

        public ContainsCategoryDeckWinrateAccumulateFunctionContext() {

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
        return new ContainsCategoryDeckWinrateAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        ContainsCategoryDeckWinrateAccumulateFunctionContext c = (ContainsCategoryDeckWinrateAccumulateFunctionContext) context;
        c.countMap = new HashMap();
    }

    /*      $winsAgainstThisCard: Map() from accumulate(
            Match( $firstPlayerCards: cardsPlayedFirstPlayer, $secondPlayerCards: cardsPlayedSecondPlayer, $firstWon: firstPlayerWon ),
            init( Map map = new HashMap(); ),
            action( List cards = null;
                    boolean mostFreqCenterpieceLost = true;
                    if ($firstWon) {
                        cards = $firstPlayerCards;
                        if (!$secondPlayerCards.contains($mostFreqCenterpiece)) mostFreqCenterpieceLost = false;
                    }
                    else {
                        cards = $secondPlayerCards;
                        if (!$firstPlayerCards.contains($mostFreqCenterpiece)) mostFreqCenterpieceLost = false;
                    }
                    if (mostFreqCenterpieceLost) {
                        for (Card card : (List<Card>)cards) {
                            if (card.getCenterpiece()) {
                                if (map.containsKey(card)) map.put(card, ((Integer)map.get(card)) + 1);
                                else map.put(card, 1);
                            }
                        }
                    } ),
            result( map )
        ) */

    public void accumulate(Serializable context, Object parameters) {
        ContainsCategoryDeckWinrateAccumulateFunctionContext c = (ContainsCategoryDeckWinrateAccumulateFunctionContext) context;

        Map map = (Map<String, Object>) parameters;
        Deck deck = (Deck) map.get("deck");
        Double winrate = (Double) map.get("winrate");
        DeckCategory category = (DeckCategory) map.get("category");

        map = c.countMap;

        if(deck.getCategory().equals(category)){
            map.put(deck, winrate);
        }


    }

    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((ContainsCategoryDeckWinrateAccumulateFunctionContext) context).countMap;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Map.class;
    }
}