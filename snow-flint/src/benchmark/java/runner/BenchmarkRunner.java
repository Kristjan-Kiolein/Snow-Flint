package runner;


import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import lib.math.KMathBenchmark;

/**
 * To run, in command-line: $ mvn clean install exec:exec
 * It is better to run the benchmark from command-line instead of IDE.
 */
public class BenchmarkRunner {

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(KMathBenchmark.IsPerfectSquare.class.getSimpleName()).forks(1).build();

		new Runner(options).run();
	}
	
}
