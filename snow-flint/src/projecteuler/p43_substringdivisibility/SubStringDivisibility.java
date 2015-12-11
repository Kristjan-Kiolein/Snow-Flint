package projecteuler.p43_substringdivisibility;

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
	
	
	
	public void solve() {
	
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
			
			for(List<Integer> combination : searchFrom.get(divider)) {
				Integer value = KLists.concatenateToInteger(combination);
				if(value%divider == 0) {
					multiplesOf.get(divider).add(combination);
				}
			}
			
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
		
		List<String> pandigitalNumbers = getPandigitalNumbers(dividers, multiplesOf);
		
		System.out.println("PanDigital numbers : " + pandigitalNumbers);	
		
//		for(Integer div : dividers) {
//			System.out.println("Divider : " + div);
//			for(List<Integer> in : multiplesOf.get(div)) {
//				System.out.println("\t" + KLists.concatenateToString(in));
//			}
//		}
		
	}



	
	private List<String> getPandigitalNumbers(Integer[] dividers, Map<Integer, Set<List<Integer>>> multiplesOf) {
		int dividerIndex = 0;
		ArrayList<String> pandigitalNumbers = new ArrayList<>();
		for (List<Integer> part : multiplesOf.get(dividers[dividerIndex])) {
			ArrayList<Integer> pandigit = new ArrayList<>();
			for (int i = part.size() - 1; i >= 0; i--) {
				pandigit.add(0, part.get(i));
			}
			getPandigitalNumbers(dividers, dividerIndex + 1, multiplesOf, pandigitalNumbers, pandigit);
		}
		
		return pandigitalNumbers;
	}
	
	
	@SuppressWarnings("unchecked")
	private void getPandigitalNumbers(Integer[] dividers, Integer dividerIndex, Map<Integer, Set<List<Integer>>> multiplesOf, List<String> pandigitalNumbers, ArrayList<Integer> pandigit) {
		
		if(dividerIndex == 2) System.out.println(KLists.concatenateToString(pandigit));
		if(dividerIndex == dividers.length) {
			Integer missingDigit = Arrays.stream(NUMBERS)
										 .filter(x -> !pandigit.contains(x))
										 .collect(Collectors.toList())
										 .get(0);
			pandigit.add(0, missingDigit);
			pandigitalNumbers.add(KLists.concatenateToString(pandigit));
		} else {
			for(List<Integer> part : multiplesOf.get(dividers[dividerIndex])) {
				if(part.get(1) == pandigit.get(0) && part.get(2) == pandigit.get(1)) {
//					System.out.println(part.get(1) + "" + part.get(2) + " := " + pandigit.get(0) + "" + pandigit.get(1));
						pandigit.add(0, part.get(0));
						getPandigitalNumbers(dividers, dividerIndex + 1, multiplesOf, pandigitalNumbers, (ArrayList<Integer>) pandigit.clone());
				}
			}
		}
	}
	
	
	
}































