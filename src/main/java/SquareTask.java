import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.core.IMap;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class SquareTask implements Callable<Integer>, Serializable, HazelcastInstanceAware {

    private transient HazelcastInstance hz;

    public void setHazelcastInstance(HazelcastInstance hz) {
        this.hz = hz;
    }

    public Integer call() throws Exception {
    	System.out.println("Calculating square of: no");
        IMap<String, Object> map = hz.getMap("context");
        Object objectSum = map.get("sum");
        Integer s = (Integer)objectSum;
        return (s*s);
    }
}
