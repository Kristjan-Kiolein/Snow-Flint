package lib.datastructures;

import java.util.Arrays;

public class KArrays {

	/**
	 * Swaps positions of two elements in array
	 * @param values array of values 
	 * @param i position of element to swap
	 * @param j position of element to swap with
	 */
	public static <T> void swap(T[] values, int i, int j) {
		if(i < 0 || j < 0 || i >= values.length || j >= values.length) return;
		T valueAtI = values[i];
		values[i] = values[j];
		values[j] = valueAtI;
	}
	
	
	/**
	 * Concatenates array element toString values and tries to parse
	 * resulting string into integer
	 * @param array array to concatenate
	 * @return resulting integer value
	 */
	public static Integer concatenateToInteger(Object[] array)  throws NumberFormatException {
		StringBuilder sb = new StringBuilder();
		Arrays.stream(array).forEach(x -> sb.append(x.toString()));
		Integer valueOf = Integer.valueOf(sb.toString());
		return valueOf;
	}

}
