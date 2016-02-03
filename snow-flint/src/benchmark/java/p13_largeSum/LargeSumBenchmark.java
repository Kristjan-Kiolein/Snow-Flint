package p13_largeSum;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import projecteuler.p013_largeSum.LargeSum;

public class LargeSumBenchmark {
	
	
	
	
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@State(Scope.Thread)
	public static class Solve{

		private List<String> numbers;
		
		@Setup(Level.Trial)
        public void doSetup() {
            numbers = LargeSum.readNumbers(Paths.get("src/main/java/projecteuler/p13_largeSum", "numbers.txt"));
        }
		
		@Benchmark
		public void consequentIntegers() {
			LargeSum.solve(numbers);
		}
	}

}
