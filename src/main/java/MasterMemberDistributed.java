import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.Member;
import com.hazelcast.durableexecutor.DurableExecutorService;
import com.hazelcast.durableexecutor.DurableExecutorServiceFuture;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;

public class MasterMemberDistributed {

    public static void main(String[] args) throws Exception {
    	
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
    
        Map<String, Integer> map = hz.getMap("map");
        for (int i = 0; i < 5; i++) {
            map.put(UUID.randomUUID().toString(), 1);
        }
        System.out.println("\nTotal Nos:" + map.keySet().size());
        DurableExecutorService executor = hz.getDurableExecutorService("executor");
        DurableExecutorServiceFuture<Integer> result = executor.submit(new SumTask());
        int sum = 0;
        Integer integer = result.get();
        SumTaskExecutionCallback sumTaskExecutionCallback = new SumTaskExecutionCallback();
        sumTaskExecutionCallback.setHazelcastInstance(hz);
        result.andThen(sumTaskExecutionCallback);
    }
}
