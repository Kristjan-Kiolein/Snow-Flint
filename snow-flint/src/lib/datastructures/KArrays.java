package lib.datastructures;

import java.lang.reflect.Array;
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
		Integer valueOf = null;
		
		if(array != null) {
			StringBuilder sb = new StringBuilder();
			Arrays.stream(array).forEach(x -> sb.append(x.toString()));
			valueOf = Integer.valueOf(sb.toString());
		}
		
		return valueOf;
	}
	
	/**
	 * Makes copy of 2D array
	 * @param array array to copy
	 * @param clazz clazz of the array objects
	 * @return a copy of original array
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[][] copyOf(T[][] array, Class<T> clazz) {
		if(array == null) return null;
		T [][] copy = (T[][]) Array.newInstance(clazz, array.length, array.length);
		for(int i = 0; i < array.length; i++)
			copy[i] = array[i].clone();
		return copy;
	}
	

}
