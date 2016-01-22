package benchmark.lib.math;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


public class KMathBenchmark {

	
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@State(Scope.Thread)
	public static class IsPerfectSquare {
		
		@Benchmark
		public void helloWorld() {
			// a dummy method to check the overhead
		}
	}
	
	
}
