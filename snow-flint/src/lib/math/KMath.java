package lib.math;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import lib.datastructures.KArrays;

public class KMath {

  
  
  
	/**
	 * Calculates permutations using the Heap's algorithm
	 * @param values  values to be permutated
	 * @param groupSize length of resulting permutation
	 * @return list of all permutations of given values
	 */
	public static <T> List<T[]> permutations(T[] values, int groupSize) {
		List<T[]> permutations = new ArrayList<>();
		List<T[]> combinations = combinations(values, groupSize);
		for(T[] combination : combinations) {
			permutations.addAll(permutations(combination));
		}
		
		return permutations;
	}
  
  
  
	/**
	 * Calculates permutations using the Heap's algorithm
	 * @param values  values to be permutated
	 * @return list of all permutations of given values
	 */
	public static <T> List<T[]> permutations(T[] values) {
		List<T[]> results = new ArrayList<>();
		permutations(values, values.length - 1, results);
		return results;
	}
  
  
  
	/**
	 * Calculates permutations using the Heap's algorithm
	 * @param values values to be permutated
	 * @param n index of element being worked on
	 * @param results list of resulting permutations 
	 */
	private static <T> void permutations(T[] values, int n, List<T[]> results) {
		if (n == 0) {
			results.add(values);
		} else {
			for (int i = 0; i < n; i++) {
				permutations(values.clone(), n - 1, results);
				
				if (n % 2 == 0) {
					KArrays.swap(values, i, n);
				} else {
					KArrays.swap(values, 0, n);
				}
			}
			permutations(values.clone(), n - 1, results);
		}
	}

	
	
	
	
	/**
	 * Finds all combinations of given elements. Handles given elements as non equal.
	 * Meaning that given list with all same elements, can still return many combinations.
	 * @param values elements whom combinations to find
	 * @return list of possible combinations
	 */
	public static <T> List<T[]> combinations(T[] values) {
		return combinations(values, values.length);
	}
	
	/**
	 * Finds all combinations of given elements of given size. Handles given elements as non equal.
	 * Meaning that given list with all same elements, can still return many combinations.
	 * @param values elements whom combinations to find
	 * @param groupSize how long combinations will be made. Mustn't be larger than input length.
	 * @return list of possible combinations
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T[]> combinations(T[] values, int groupSize) {
		if (groupSize > values.length) return null;
		Class<?> type =  values.getClass().getComponentType();
		ArrayList<T[]> results = new ArrayList<T[]>();
		T[] result = (T[]) Array.newInstance(type, groupSize);
		combinations(values, groupSize, 0, result, results);
		return results;
	}
    
    /**
  	 * Finds all combinations of given elements of given size. Handles given elements as non equal.
  	 * Meaning that given list with all same elements, can still return many combinations.
  	 * @param values elements whom combinations to find
  	 * @param groupSize how long combinations will be made. Mustn't be larger than input length.
     * @param startPosition position where to set next element
     * @param result one combination
     * @param results all possible combinations
     */
	private static <T> void combinations(T[] values, int groupSize, int startPosition, T[] result, List<T[]> results) {
		if (groupSize == 0) {
			results.add(result.clone());
			return;
		}
		for (int i = startPosition; i <= values.length - groupSize; i++) {
			result[result.length - groupSize] = values[i];
			combinations(values, groupSize - 1, i + 1, result, results);
		}
	}

}
