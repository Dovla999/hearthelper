package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.Card;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyCenterpieceCardCountAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class EnemyCenterpieceCardCountAccumulateFunctionContext implements Externalizable {

        public Map countMap = new HashMap();

        public EnemyCenterpieceCardCountAccumulateFunctionContext() {

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
        return new EnemyCenterpieceCardCountAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        EnemyCenterpieceCardCountAccumulateFunctionContext c = (EnemyCenterpieceCardCountAccumulateFunctionContext) context;
        c.countMap = new HashMap();
    }

    /*      $countMap: Map() from accumulate(
            Match( $enemyCards: cardsPlayedSecondPlayer ) from $pmh,
            init( Map enemyCenterpieceCount = new HashMap(); ),
            action( for (Card card : (List<Card>)$enemyCards) {
                        if (card.getCenterpiece()) {
                            if (enemyCenterpieceCount.containsKey(card)) enemyCenterpieceCount.put(card, ((Integer)enemyCenterpieceCount.get(card)) + 1);
                            else enemyCenterpieceCount.put(card, 1);
                        }
                    } ),
            result( enemyCenterpieceCount )
        )*/

    public void accumulate(Serializable context, Object value) {
        EnemyCenterpieceCardCountAccumulateFunctionContext c = (EnemyCenterpieceCardCountAccumulateFunctionContext) context;

        List<Card> enemyCards = (List<Card>) value;
        Map enemyCenterpieceCount = c.countMap;

        for (Card card : enemyCards) {
            if (card.getCenterpiece()) {
                if (enemyCenterpieceCount.containsKey(card)) enemyCenterpieceCount.put(card, ((Integer)enemyCenterpieceCount.get(card)) + 1);
                else enemyCenterpieceCount.put(card, 1);
            }
        }
    }

    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((EnemyCenterpieceCardCountAccumulateFunctionContext) context).countMap;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Map.class;
    }
}
