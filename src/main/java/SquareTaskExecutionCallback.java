import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.durableexecutor.DurableExecutorService;

public class SquareTaskExecutionCallback implements ExecutionCallback<Integer>, HazelcastInstanceAware{

	private transient HazelcastInstance hz;
	@Override
	public void onFailure(Throwable e) {
		System.out.println("\nSquareTask Execution failed");
		e.printStackTrace();
	}

	@Override
	public void onResponse(Integer i) {
	 System.out.print("\nSquare Task Result:" + i);
	 hz.getMap("context").put("result",i);
	}

	@Override
	public void setHazelcastInstance(HazelcastInstance hz) {
		this.hz = hz;
		
	}

}
