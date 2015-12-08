package projecteuler.substringdivisibility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  <b> Problem 43 </b>
 *  <br><br>
 *	The number, 1406357289, is a 0 to 9 pandigital number because it is made up of each of the digits 0 to 9 in some order, but it also has a rather interesting sub-string divisibility property.
 *	<br><br>		
 *	Let d1 be the 1st digit, d2 be the 2nd digit, and so on. In this way, we note the following:
 *	<br>
 *	d2d3d4=406 is divisible by 2<br>
 *	d3d4d5=063 is divisible by 3<br>
 *	d4d5d6=635 is divisible by 5<br>
 *	d5d6d7=357 is divisible by 7<br>
 *	d6d7d8=572 is divisible by 11<br>
 *	d7d8d9=728 is divisible by 13<br>
 *	d8d9d10=289 is divisible by 17<br><br>
 *	<i>Find the sum of all 0 to 9 pandigital numbers with this property.</i> 
 */

@SuppressWarnings("unused")
public class SubStringDivisibility {
	
	private static final int[] DIVIDERS = {2, 3, 5, 7, 11, 13, 17};
	private static final int[] NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private static final int[] ODD_NUMBERS = {1, 3, 5, 7, 9};
	private static final int[] EVEN_NUMBERS = {0, 2, 4, 6, 8};
	
	
	private Map<Integer, Map<Set<Integer>, String>> solutions;
	private List<Integer> permutations;
	
	public SubStringDivisibility() {
		solutions = new HashMap<>();
		for(int divider : DIVIDERS) {
			solutions.put(divider, new HashMap<>());
		}
		
	}
	
}
