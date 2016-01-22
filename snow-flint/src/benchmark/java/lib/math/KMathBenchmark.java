package lib.math;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;


public class KMathBenchmark {

	private static final long NR_OF_TEST_RUNS = 1_000_000;
	
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@State(Scope.Thread)
	public static class IsPerfectSquare {

		@Benchmark
		public void consequentIntegers() {
			for(long i = 0; i < NR_OF_TEST_RUNS; i++) {
				KMath.isPerfectSquare(i);
			}
		}
	}
	
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@State(Scope.Thread)
	public static class IsPerfectSquareSimple {
		
		@Benchmark
		@SuppressWarnings("deprecation")
		public void consequentIntegers() {
			for(long i = 0; i < NR_OF_TEST_RUNS; i++) {
				KMath.isPerfectSquareSimple(i);
			}
		}
	}
	
	
}
