package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WinsAgainstThisHeroCountAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class WinsAgainstThisHeroCountAccumulateFunctionContext implements Externalizable {

        public Map countMap = new HashMap();

        public WinsAgainstThisHeroCountAccumulateFunctionContext() {

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
        return new WinsAgainstThisHeroCountAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        WinsAgainstThisHeroCountAccumulateFunctionContext c = (WinsAgainstThisHeroCountAccumulateFunctionContext) context;
        c.countMap = new HashMap();
    }

    /*      $winsAgainstThisHero: Map() from accumulate(
            Match( $firstPlayer: firstPlayer, $secondPlayer: secondPlayer, $firstWon: firstPlayerWon ),
            init( Map map = new HashMap(); ),
            action( if ($firstPlayer == $mostFreqHero && !$firstWon) {
                        if (map.containsKey($secondPlayer)) map.put($secondPlayer, ((Integer)map.get($secondPlayer)) + 1);
                        else map.put($secondPlayer, 1);
                   } else if ($secondPlayer == $mostFreqHero && $firstWon) {
                        if (map.containsKey($firstPlayer)) map.put($firstPlayer, ((Integer)map.get($firstPlayer)) + 1);
                        else map.put($firstPlayer, 1);
                   } ),
            result( map )
        ) */

    public void accumulate(Serializable context, Object parameters) {
        WinsAgainstThisHeroCountAccumulateFunctionContext c = (WinsAgainstThisHeroCountAccumulateFunctionContext) context;

        Map map = (Map<String, Object>) parameters;
        Hero firstPlayer = (Hero) map.get("firstPlayer");
        Hero secondPlayer = (Hero) map.get("secondPlayer");
        Boolean firstWon = (Boolean) map.get("firstWon");
        Hero mostFreqHero = (Hero) map.get("mostFreqHero");

        map = c.countMap;

        if (firstPlayer == mostFreqHero && !firstWon) {
            if (map.containsKey(secondPlayer)) map.put(secondPlayer, ((Integer)map.get(secondPlayer)) + 1);
            else map.put(secondPlayer, 1);
        } else if (secondPlayer == mostFreqHero && firstWon) {
            if (map.containsKey(firstPlayer)) map.put(firstPlayer, ((Integer)map.get(firstPlayer)) + 1);
            else map.put(firstPlayer, 1);
        }
    }

    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((WinsAgainstThisHeroCountAccumulateFunctionContext) context).countMap;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Map.class;
    }
}
