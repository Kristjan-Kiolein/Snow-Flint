package projecteuler.p020_p029.p023_nonAbundantSums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lib.math.KMath;

/**
 * <h1>Non-abundant sums</h1>
 *<br>
 * A perfect number is a number for which the sum of its proper divisors is exactly equal to the number.
 * For example, the sum of the proper divisors of 28 would be 1 + 2 + 4 + 7 + 14 = 28, which means that 28 is a perfect number.<br>
 * <br>
 * A number n is called deficient if the sum of its proper divisors is less than n and it is called abundant if this sum exceeds n.<br>
 * <br>
 * As 12 is the smallest abundant number, 1 + 2 + 3 + 4 + 6 = 16, the smallest number that can be written as the sum of two abundant numbers is 24.
 * By mathematical analysis, it can be shown that all integers greater than 28123 can be written as the sum of two abundant numbers.
 * However, this upper limit cannot be reduced any further by analysis even though it is known that the greatest number that cannot be expressed
 * as the sum of two abundant numbers is less than this limit.<br>
 * <br>
 * <i>Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers.</i><br>
 */
public class NonAbundantSums {

	private static final int PROVED_MAXIMUM = 28123;
	
	public static void main(String[] args) {
	
		long t = System.currentTimeMillis();
		int sum = solve();
		
		System.out.println("Sum : " + sum + " in " + (System.currentTimeMillis() - t) + "");
		
	}

	/**
	 * Solution should be found with boolean array instead of lists to make it faster
	 */
	private static int solve() {
		List<Integer> abundantNumbers = findAllAbundantNumbers();
		List<Integer> missingSums = new ArrayList<>();
		Set<Integer> possibleSums = new HashSet<>();
		
		for (int i = 0; i < PROVED_MAXIMUM + 1; i++) {
			missingSums.add(i);
		}

		for (int i = 0; i < abundantNumbers.size(); i++) {
			for (int j = i; j < abundantNumbers.size(); j++) {
				int sum = abundantNumbers.get(i) + abundantNumbers.get(j);
				if(sum > PROVED_MAXIMUM) break;
				possibleSums.add(sum);
			}
		}
		
		missingSums.removeAll(possibleSums);
		
		return missingSums.stream().mapToInt(x -> x).sum();
	}
	
	private static List<Integer> findAllAbundantNumbers() {
		List<Integer> abundantNumbers = new ArrayList<>();
		
		for(int i = 1; i <= PROVED_MAXIMUM; i++) {
			if(KMath.divisorsSum(i) > i) {
				abundantNumbers.add(i);
			}
		}
		return abundantNumbers;
	}
}
