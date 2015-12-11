package lib.datastructures;

import java.util.List;

public class KLists {

	
	/**
	 * Swaps positions of two elements in list
	 * @param values array of values 
	 * @param i position of element to swap
	 * @param j position of element to swap with
	 */
	public static <T> void swap(List<T> values, int i, int j) {
		if(i < 0 || j < 0 || i >= values.size()|| j >= values.size()) return;
		T valueAtI = values.get(i);
		values.set(i, values.get(j));
		values.set(j, valueAtI);
	}

	/**
	 * Concatenates array element toString values and tries to parse
	 * resulting string into integer
	 * @param list array to concatenate
	 * @return resulting integer value
	 */
	public static <T> Integer concatenateToInteger(List<T> list)  throws NumberFormatException {
		Integer valueOf = null;
		if (list != null) {
			StringBuilder sb = new StringBuilder();
			list.stream().forEach(x -> sb.append(x.toString()));
			valueOf = Integer.valueOf(sb.toString());
		}
		return valueOf;
	}

	
	/**
	 * Concatenates array element toString values 
	 * @param list array to concatenate
	 * @return resulting string value
	 */
	public static <T> String concatenateToString(List<T> list) {
		StringBuilder sb = new StringBuilder(); 
		if (list != null) {
			list.stream().forEach(x -> sb.append(x.toString()));
		}
		
		return sb.toString();
	}
	
}
