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
        Double val = 0.0;
        try {
            val = (Double) map.get("val");
        }catch (Exception e){
            val = Double.valueOf((Integer) map.get("val"));
        }
        Double k = 0.0;

        Map countMap = (Map) map.get("map");
        try {
            k = (Double) countMap.get(c.returnKey);
        }catch (Exception e){
            k = Double.valueOf((Integer) countMap.get(c.returnKey));
        }

        if (c.returnKey == null || val > k)
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
