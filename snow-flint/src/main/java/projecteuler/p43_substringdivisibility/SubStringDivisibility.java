package projecteuler.p43_substringdivisibility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import lib.datastructures.KLists;
import lib.math.KMath;

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

public class SubStringDivisibility {
	
	private static final int PERMUTATIONS_SIZE = 3;
	private static final Integer[] DIVIDERS = {17, 13, 11, 7, 5, 3, 2};
	private static final Integer[] NUMBERS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private static final Comparator<Integer> REVERSE_ORDER = new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2.compareTo(o1);
		}
	};
	
	public BigInteger solve() {
	
		Integer[] dividers = DIVIDERS.clone(); 
		Arrays.sort(dividers, REVERSE_ORDER);
		
//		Map<Integer, Integer[]> allCombinations = KMath.permutations(NUMBERS, PERMUTATIONS_SIZE).stream().collect(Collectors.toMap(KArrays::concatenateToInteger, x -> x ));
		List<List<Integer>> allCombinations = KMath.permutations(NUMBERS, PERMUTATIONS_SIZE).stream().map(x -> Arrays.asList(x)).collect(Collectors.toList());
		Map<Integer, Set<List<Integer>>> searchFrom = new HashMap<>();
		Map<Integer, Set<List<Integer>>> multiplesOf = new HashMap<>();
		Arrays.stream(dividers).forEach(d -> searchFrom.put(d, new HashSet<>()));
		Arrays.stream(dividers).forEach(d -> multiplesOf.put(d, new HashSet<>() ));
		searchFrom.put(dividers[0], new HashSet<>(allCombinations));
		
		
		for (int i = 0; i < dividers.length; i++) {
			Integer divider = dividers[i];
			
			//Get numbers dividable 
			for(List<Integer> combination : searchFrom.get(divider)) {
				Integer value = KLists.concatenateToInteger(combination);
				if(value%divider == 0) {
					multiplesOf.get(divider).add(combination);
				}
			}
			
			//Make next set of numbers to look from			
			if(i < dividers.length - 1) {	
				for(List<Integer> combination : allCombinations) {
					for(List<Integer> multiple : multiplesOf.get(divider)) {
						if(combination.get(1) == multiple.get(0) && combination.get(2) == multiple.get(1)) {
							searchFrom.get(dividers[i + 1]).add(combination);
						}
					}
				}
			}
		}
		
		List<String> pandigitalNumbers = generatePandigitalNumbers(dividers, multiplesOf);
		
		return pandigitalNumbers.stream().map(BigInteger::new).reduce(BigInteger.ZERO, BigInteger::add);
		
	}



	
	private List<String> generatePandigitalNumbers(Integer[] dividers, Map<Integer, Set<List<Integer>>> multiplesOf) {
		int dividerIndex = 0;
		ArrayList<String> pandigitalNumbers = new ArrayList<>();
	
		//All starting with numbers that are divisible by 17
		for (List<Integer> part : multiplesOf.get(dividers[dividerIndex])) {
			
			ArrayList<Integer> pandigit = new ArrayList<>();
			pandigit.addAll(part); //Last three numbers added
			getPandigitalNumbers(dividers, dividerIndex + 1, multiplesOf, pandigitalNumbers, pandigit);
		}
		
		return pandigitalNumbers;
	}
	
	
	@SuppressWarnings("unchecked")
	private void getPandigitalNumbers(Integer[] dividers, Integer dividerIndex, Map<Integer, Set<List<Integer>>> multiplesOf, List<String> pandigitalNumbers, ArrayList<Integer> pandigit) {
		
		if(dividerIndex == dividers.length) {
			//Get the digit not already in the number
			Integer missingDigit = Arrays.stream(NUMBERS)
										 .filter(x -> !pandigit.contains(x))
										 .collect(Collectors.toList())
										 .get(0);
			if(missingDigit != 0) {
				pandigit.add(0, missingDigit);
				pandigitalNumbers.add(KLists.concatenateToString(pandigit));
			}
			
		} else {
			for(List<Integer> part : multiplesOf.get(dividers[dividerIndex])) {
				if(part.get(1) == pandigit.get(0) && part.get(2) == pandigit.get(1) && !pandigit.contains(part.get(0))) {
						ArrayList<Integer> digitClone = (ArrayList<Integer>) pandigit.clone();
						digitClone.add(0, part.get(0));
						getPandigitalNumbers(dividers, dividerIndex + 1, multiplesOf, pandigitalNumbers, digitClone);
				}
			}
		}
	}
	
	
	
}



/* Real solution
 
Just to reiterate, a pandigital number is one in which each digit occurs only once. This is how we proceed:

	d_4d_5d_6 is divisible by 5
	This implies that d_6 is either 0 or 5.
	d_6d_7d_8 is divisible by 11
	This implies that d_6 + d_8 - d_7 is a  multiple of 11.
	If d_6 were to be 0, it would mean that d_8 = d_7 or d_8 = d_7 + 11, neither of which can be true given that the number we seek is pandigital.
	So we can assert that d_6 = 5.
	We need all the pairs of d_7 and d_8 satisfying d_8 - d_7 = 6 or d_8 - d_7 = -5.
	Possible (d_7, d_8) pairs are (6,1), (7,2), (8,3), (9,4), (1,7), (2,8), (3,9)
	d_5d_6d_7 is divisible by 7
	
This essentially means that 10*d_5 + d_6 - 2*d_7 is a multiple of 7. We already have the value for d_6 and have narrowed down the possible values of d_7.
	d_6, d_7 = 5, 6 \Rightarrow d_5 = 7
	d_6, d_7 = 5, 7 \Rightarrow d_5 = 3
	d_6, d_7 = 5, 8 \Rightarrow d_5 = 6
	d_6, d_7 = 5, 9 \Rightarrow d_5 = 2
	d_6, d_7 = 5, 1 \Rightarrow d_5 = 6
	d_6, d_7 = 5, 2 \Rightarrow d_5 = 9
	d_6, d_7 = 5, 3 \Rightarrow d_5 = NA
Possible (d5, d6, d7, d8) tuples are (7,5,6,1), (3,5,7,2), (6,5,8,3), (2,5,9,4), (6,5,1,7) or (9,5,2,8).
d_7d_8d_9 is divisible by 13


This implies that d_9 + 10*d_8 + 9*d_7 is a multiple of 13. We have already identified the possible d_7, d_8 pairs. We can get the possible d_9 values now.
	d_7, d_8 = 6, 1 \Rightarrow d_9 = NA
	d_7, d_8 = 7, 2 \Rightarrow d_9 = 8
	d_7, d_8 = 8, 3 \Rightarrow d_9 = 2
	d_7, d_8 = 9, 4 \Rightarrow d_9 = NA
	d_7, d_8 = 1, 7 \Rightarrow d_9 = NA
	d_7, d_8 = 2, 8 \Rightarrow d_9 = 6
Possible (d_5, d_6, d_7, d_8, d_9) tuples are (3,5,7,2,8), (6,5,8,3,2) or (9,5,2,8,6).
d_8d_9d_{10} is divisible by 17

This implies that 10*d_8 + d_9 - 5*d_{10} is a multiple of 17. Again using the possible values of the other two variables, we can narrow down the value of the third one.
	d_8, d_9 = 2, 8 \Rightarrow d_{10} = 9
	d_8, d_9 = 3, 2 \Rightarrow d_{10} = NA
	d_8, d_9 = 8, 6 \Rightarrow d_{10} = 7
So we now have the following tuples for the values of (d_5, d_6, d_7, d_8, d_9, d_{10}): (3,5,7,2,8,9) or (9,5,2,8,6,7).

d_2d_3d_4 is divisible by 2 tells us that d_4 is even. So in the first case it can take the values 0,4,6 and in the second case the values 0,4.

Finally, using the last condition that d_3d_4d_5 is divisible by 3, we conclude that d_3, d_4 in the first case can be either of 6,0 or 0,6 and in the second case is 0,9.

Thus we have the six possible numbers as: 1406357289, 1460357289, 4106357289, 4160357289, 1430952867 and 4130952867. And we are done! Sum of these 6 numbers is what the problem asked for.
 */