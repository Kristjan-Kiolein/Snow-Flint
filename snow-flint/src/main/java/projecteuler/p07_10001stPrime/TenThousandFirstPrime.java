package projecteuler.p07_10001stPrime;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>10001st prime</b><br>
 * By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.<br>
 * <i>What is the 10 001st prime number?</i>
 */
public class TenThousandFirstPrime {

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		System.out.println(solve());
		System.out.println(System.currentTimeMillis() - t);
	}
	
	public static long solve() {
		int nthPrime = 10001;
		List<Integer> foundPrimes = new ArrayList<>();	

		int current = 1;
		WhileLoop:
		while(foundPrimes.size() != nthPrime) {
			current++;
			
			for(Integer prime : foundPrimes) {
				if(current % prime == 0) 
					continue WhileLoop;
			}
			
//			System.out.println(foundPrimes.size());
			foundPrimes.add(current);
		}
		
		return foundPrimes.get(foundPrimes.size() - 1);
	}
}
