package lib.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KMath {

	/**
	 * Finds all combinations of given elements. Handles given elements as non equal.
	 * Meaning that given list with all same elements, can still return many combinations.
	 * @param input elements whom combinations to find
	 * @param length how long combinations will be made. Mustn't be larger than input length.
	 * @return list of possible combinations
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T[]> combinations(T[] input, int length){
		if(length > input.length) return null;
    	ArrayList<T[]> results = new ArrayList<T[]>();
		combinations(input, length, 0, (T[]) new Object[length], results);
		return results;
    }
    
    /**
	 * Finds all combinations of given elements. Handles given elements as non equal.
	 * Meaning that given list with all same elements, can still return many combinations.
	 * @param input elements whom combinations to find
	 * @param length how long combinations will be made. Mustn't be larger than input length.
     * @param startPosition position where to set next element
     * @param result one combination
     * @param results all possible combinations
     */
    private static <T> void combinations(T[] input, int length, int startPosition, T[] result, List<T[]> results){
        if (length == 0){
            results.add(Arrays.copyOf(result, result.length));
            return;
        }       
        for (int i = startPosition; i <= input.length-length; i++){
            result[result.length - length] = input[i];
            combinations(input, length-1, i+1, result, results);
        }
    }       
}
