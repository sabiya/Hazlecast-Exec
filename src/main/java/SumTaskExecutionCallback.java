import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.ExecutionCallback;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastInstanceAware;
import com.hazelcast.durableexecutor.DurableExecutorService;
import com.hazelcast.durableexecutor.DurableExecutorServiceFuture;
import com.hazelcast.spi.AbstractDistributedObject;
import com.hazelcast.spi.exception.DistributedObjectDestroyedException;

public class SumTaskExecutionCallback implements ExecutionCallback<Integer>, HazelcastInstanceAware{

	private transient HazelcastInstance hz;
	@Override
	public void onFailure(Throwable e) {
		System.out.println("SumTask Execution failed");
		e.printStackTrace();
	}

	@Override
	public void onResponse(Integer i) {
	 System.out.print("\nSumTask Result:" + i);
	 
	 DurableExecutorService executorService = hz.getDurableExecutorService("executor");
	 hz.getMap("context").put("sum", i);
	 DurableExecutorServiceFuture<Integer> squareFuture = executorService.submit(new SquareTask());
	 System.out.println("Submitted square task , now going to sleep");
	 try {
		Thread.sleep(1*60*1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 SquareTaskExecutionCallback squareTaskExecutionCallback = new SquareTaskExecutionCallback();
	 squareTaskExecutionCallback.setHazelcastInstance(hz);
	 squareFuture.andThen(squareTaskExecutionCallback);
	}

	@Override
	public void setHazelcastInstance(HazelcastInstance hz) {
		this.hz = hz;
		
	}




}
