package benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {

	@Benchmark
	public void helloWorld() {
		// a dummy method to check the overhead
	}

	/*
	 * It is better to run the benchmark from command-line instead of IDE.
	 * 
	 * To run, in command-line: $ mvn clean install exec:exec
	 */

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder()
				.include(BenchmarkRunner.class.getSimpleName()).forks(1).build();

		new Runner(options).run();
	}
	
}
