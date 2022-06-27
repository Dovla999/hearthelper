package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HeroClassWithMostCardsAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class HeroClassWithMostCardsAccumulateFunctionContext implements Externalizable {

        public Map countMap = new HashMap();

        public HeroClassWithMostCardsAccumulateFunctionContext() {

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
        return new HeroClassWithMostCardsAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        HeroClassWithMostCardsAccumulateFunctionContext c = (HeroClassWithMostCardsAccumulateFunctionContext) context;
        c.countMap = new HashMap();
    }

    /*         $countMap: Map() from accumulate(
            Match( $hero: secondPlayer ) from $pmh,
            init( Map enemyHeroCount = new HashMap(); ),
            action( if (enemyHeroCount.containsKey($hero)) enemyHeroCount.put($hero, ((Integer)enemyHeroCount.get($hero)) + 1);
                    else enemyHeroCount.put($hero, 1); ),
            result( enemyHeroCount )
        ) */

    public void accumulate(Serializable context, Object value) {
        HeroClassWithMostCardsAccumulateFunctionContext c = (HeroClassWithMostCardsAccumulateFunctionContext) context;

        if( value.equals(Hero.NEUTRAL)) return;

        if (c.countMap.containsKey(value)) c.countMap.put(value, ((Integer)c.countMap.get(value)) + 1);
        else c.countMap.put(value, 1);
    }

    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((HeroClassWithMostCardsAccumulateFunctionContext) context).countMap;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Map.class;
    }
}
