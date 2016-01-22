package lib.math;

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
		
		private static final int NR_OF_TEST = 1_000_000;
		
		@Benchmark
		public void consequentIntegers() {
			for(int i = 0; i < NR_OF_TEST; i++) {
				KMath.isPerfectSquare(i);
			}
		}
	}
	
	
}
