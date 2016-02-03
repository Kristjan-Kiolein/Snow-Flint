package projecteuler.p020_p029.p021_amicableNumbers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <h1>Amicable numbers</h1>
 * <br>
 * Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).<br>
 * If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are called amicable numbers.<br>
 * <br>
 * For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284.
 * The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.<br>
 * <br>
 * <i>Evaluate the sum of all the amicable numbers under 10000.</i>
 */
public class AmicableNumbers {

	
	public static void main(String[] args) {
		int sum = solve(10000);
		System.out.println(sum);
	}

	private static int solve(int limit) {
		
		Map<Integer, List<Integer>> calculatedDivisors = new HashMap<>();
		Set<Integer> amicableNumbers = new HashSet<>();
		
		for(int i = 1; i < limit; i++) {
			calculatedDivisors.put(i, getDivisors(i));
		}
		
		for(Entry<Integer, List<Integer>> entry : calculatedDivisors.entrySet()) {
			
			int sum1 = entry.getValue().stream().mapToInt(x -> x).sum();
			
			if( calculatedDivisors.get(sum1) != null) {
				int sum2 = calculatedDivisors.get(sum1).stream().mapToInt(x -> x).sum();
				
				if(entry.getKey() == sum2 && sum1 != sum2) {
					amicableNumbers.add(sum1);
					amicableNumbers.add(sum2);
				}
			}
		}
		
		
		return amicableNumbers.stream().mapToInt(x -> x).sum();
	}

	
	/**
	 * To find all pairs we need only to check up to sqrt(n) !
	 * A second improvement comes from the realisation that odd numbers cannot have even
 	 * numbers as divisors. (The converse is not true: even numbers can have odd divisors). 
	 */
	private static List<Integer> getDivisors(int target) {
		List<Integer> divisors = new ArrayList<>();
		
		
		for(int i = 1; i < target; i++) {
			if(target%i == 0) divisors.add(i);
		}
		
		return divisors;
	}
	
}
