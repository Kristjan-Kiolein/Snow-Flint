package runner;


import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import p13_largeSum.LargeSumBenchmark;

/**
 * To run, in command-line: $ mvn clean install exec:exec
 * It is better to run the benchmark from command-line instead of IDE.
 */
public class BenchmarkRunner {

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
//				.include(KMathBenchmark.IsPerfectSquare.class.getSimpleName())
//				.include(KMathBenchmark.IsPerfectSquareSimple.class.getSimpleName())
				.include(LargeSumBenchmark.Solve.class.getSimpleName())
				.forks(1)
				.warmupIterations(10)
				.measurementIterations(10)
				.mode(Mode.AverageTime)
				.build();

		new Runner(options).run();
	}
	
}
