package projecteuler.p10_summationOfPrimes;

import java.util.List;

import lib.math.SieveOfAtkin;

/**
 * <b>Summation of primes</b><br>
 * The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.<br>
 * <i>Find the sum of all the primes below two million.</i><br>
 */
public class SummationOfPrimes {
	
	
	public static void main(String[] args) {
		System.out.println(solve());
	}
	
	
	private static long solve() {
		
		int primesBelow = 2000000;
		
		List<Integer> primes = SieveOfAtkin.findPrimes(primesBelow);
		
		return primes.stream().mapToLong(i -> i).sum();
	}
	

}
