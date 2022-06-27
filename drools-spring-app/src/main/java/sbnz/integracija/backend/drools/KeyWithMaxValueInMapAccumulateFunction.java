package sbnz.integracija.backend.drools;

import sbnz.integracija.backend.facts.Hero;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class KeyWithMaxValueInMapAccumulateFunction implements org.kie.api.runtime.rule.AccumulateFunction {
    public static class KeyWithMaxValueInMapAccumulateFunctionContext implements Externalizable {

        public Object returnKey = null;

        public KeyWithMaxValueInMapAccumulateFunctionContext() {

        }

        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(returnKey);
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            returnKey = in.readObject();
        }

    }

    public void writeExternal(ObjectOutput out) throws IOException {

    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }

    public Serializable createContext() {
        return new KeyWithMaxValueInMapAccumulateFunctionContext();
    }

    public void init(Serializable context) throws Exception {
        KeyWithMaxValueInMapAccumulateFunctionContext c = (KeyWithMaxValueInMapAccumulateFunctionContext) context;
        c.returnKey = null;
    }

    //        $mostFreq: Hero() from accumulate(
//            Map.Entry( $key: key, $val: value ) from $countMap.entrySet(),
//            init( Hero mostFrequentHero = null; ),
//            action( if (mostFrequentHero == null || ((Integer)$val) > ((Integer)$countMap.get(mostFrequentHero))) mostFrequentHero = (Hero)$key; ),
//            result( mostFrequentHero )
//        )

    public void accumulate(Serializable context, Object parameters) {
        KeyWithMaxValueInMapAccumulateFunctionContext c = (KeyWithMaxValueInMapAccumulateFunctionContext) context;

        Map map = (Map<String, Object>) parameters;
        Object key = map.get("key");
        Double val = (Double) map.get("val");
        Map countMap = (Map) map.get("map");

        if (c.returnKey == null || val > (Double) countMap.get(c.returnKey))
            c.returnKey = key;
    }

    public void reverse(Serializable context, Object value) throws Exception {

    }

    public Object getResult(Serializable context) throws Exception {
        return ((KeyWithMaxValueInMapAccumulateFunctionContext) context).returnKey;
    }

    public boolean supportsReverse() {
        return false;
    }

    public Class<?> getResultType() {
        return Object.class;
    }
}
