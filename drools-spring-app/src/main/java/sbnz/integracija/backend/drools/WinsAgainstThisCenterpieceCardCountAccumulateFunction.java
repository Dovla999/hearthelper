package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.Card;
import sbnz.integracija.backend.facts.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinsAgainstThisCenterpieceCardCountAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class WinsAgainstThisCenterpieceCardCountAccumulateFunctionContext implements Externalizable {

        public Map countMap = new HashMap();

        public WinsAgainstThisCenterpieceCardCountAccumulateFunctionContext() {

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
        return new WinsAgainstThisCenterpieceCardCountAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        WinsAgainstThisCenterpieceCardCountAccumulateFunctionContext c = (WinsAgainstThisCenterpieceCardCountAccumulateFunctionContext) context;
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
        WinsAgainstThisCenterpieceCardCountAccumulateFunctionContext c = (WinsAgainstThisCenterpieceCardCountAccumulateFunctionContext) context;

        Map map = (Map<String, Object>) parameters;
        List<Card> firstPlayerCards = (List<Card>) map.get("firstPlayerCards");
        List<Card> secondPlayerCards = (List<Card>) map.get("secondPlayerCards");
        Boolean firstWon = (Boolean) map.get("firstWon");
        Card mostFreqCenterpiece = (Card) map.get("mostFreqCenterpiece");

        map = c.countMap;

        List cards = null;
        boolean mostFreqCenterpieceLost = true;
        if (firstWon) {
            cards = firstPlayerCards;
            if (!secondPlayerCards.contains(mostFreqCenterpiece)) mostFreqCenterpieceLost = false;
        }
        else {
            cards = secondPlayerCards;
            if (!firstPlayerCards.contains(mostFreqCenterpiece)) mostFreqCenterpieceLost = false;
        }
        if (mostFreqCenterpieceLost) {
            for (Card card : (List<Card>)cards) {
                if (card.getCenterpiece()) {
                    if (map.containsKey(card)) map.put(card, ((Integer)map.get(card)) + 1);
                    else map.put(card, 1);
                }
            }
        }
    }

    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((WinsAgainstThisCenterpieceCardCountAccumulateFunctionContext) context).countMap;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Map.class;
    }
}
