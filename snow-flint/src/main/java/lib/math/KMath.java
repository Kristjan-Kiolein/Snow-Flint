package lib.math;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.datastructures.KArrays;

public class KMath {

  
	
	/**
	 * Calculates permutations using the Heap's algorithm
	 * @param values  values to be permutated
	 * @return list of all permutations of given values
	 **/
	public static <T> List<T[]> permutations(T[] values) {
		List<T[]> results = new ArrayList<>();
		permutations(values, values.length, results);
		return results;
	}
	
	/**
	 * Calculates permutations using the Heap's algorithm
	 * @param values  values to be permutated
	 * @param groupSize length of resulting permutation
	 * @return list of all permutations of given values
	 **/
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
	 * @param values values to be permutated
	 * @param n index of element being worked on
	 * @param results list of resulting permutations 
	 **/
	private static <T> void permutations(T[] values, int n, List<T[]> results) {
		
		if (n == 1) {
			results.add(values.clone());
		} else {
			for (int i = 0; i < n; i++) {
				permutations(values, n-1, results);
				if (n % 2 == 1) {
					KArrays.swap(values, 0, n-1);
				} else {
					KArrays.swap(values, i, n-1);
				}
			}
		}
	}

	/**
	 * Finds all combinations of given elements. Handles given elements as non equal.
	 * Meaning that given list with all same elements, can still return many combinations.
	 * @param values elements whom combinations to find
	 * @return list of possible combinations
	 **/
	public static <T> List<T[]> combinations(T[] values) {
		return combinations(values, values.length);
	}

	/**
	 * Finds all combinations of given elements of given size. Handles given elements as non equal.
	 * Meaning that given list with all same elements, can still return many combinations.
	 * @param values elements whom combinations to find
	 * @param groupSize how long combinations will be made. Mustn't be larger than input length.
	 * @return list of possible combinations
	 **/
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
     * @param n shows which element of array is being set
     * @param result one combination
     * @param results all possible combinations
     **/
	private static <T> void combinations(T[] values, int groupSize, int startPosition, T[] result, List<T[]> results) {
		if (groupSize == 0) {
			results.add(result.clone());
			return;
		}
		for (int i = startPosition; i <= values.length - groupSize; i++) {
			result[result.length - groupSize] = values[i]; // use groupSize instead of result.length
			combinations(values, groupSize - 1, i + 1, result, results);
		}
	}
	
	/**
	 * Perfect square is n = a*a where a is integer.
	 * Algorithm may not be very fast!
	 * @param n integer to check if it is perfect square
	 * @return true if is square of integer, false otherwise
	 */
	public final static boolean isPerfectSquare(long n)
	{
	    if (n < 0)
	        return false;

	    switch((int)(n & 0xF))
	    {
	    case 0: case 1: case 4: case 9:
	        long tst = (long)Math.sqrt(n);
	        return tst*tst == n;

	    default:
	        return false;
	    }
	}
	
	/**
	 * TAKES SQRT OF NUMBER AND THEN SQUARES IT AND COMPARES TO INPUT!
	 * SLOW!
	 * Perfect square is n = a*a where a is integer.
	 * @param n integer to check if it is perfect square
	 * @return true if is square of integer, false otherwise
	 */
	@Deprecated
	public final static boolean isPerfectSquareSimple(long n) {
		long tst = (long)Math.sqrt(n);
		return tst*tst == n;
	}
	
	
	
	/**
	 * Multiplies two positive numbers no matter the size.
	 * Uses "Long multiplication" algorithm.
	 * @param n1 positive number to multiply
	 * @param n2 positive number to multiply
	 * @return multiplication value of n1 and n2 (n1*n2), null if any inputs is null or empty.
	 * @throws RuntimeException if one of the number strings contains a non digit character 
	 * @see <a href="https://en.wikipedia.org/wiki/Multiplication_algorithm#Long_multiplication">wikipedia article</a>
	 */
	public static String multiply(String n1, String n2) throws RuntimeException{

		if(n1 == null || n2 == null || n1.isEmpty() || n2.isEmpty()) return null;
		
		byte[] n1digits = new byte[n1.length()];
		byte[] n2digits = new byte[n2.length()];
		
		for (int i = 0; i < n1.length(); i++) {
			byte value = (byte) Character.getNumericValue(n1.charAt(i));
			if(value < 0) {
				String message = "Given number (" + n1 + ") contains a non digit character : " + n1.charAt(i) + " at " + i;
//				throw new ParseException(message, 1);
				throw new RuntimeException(message);
			}
			n1digits[i] = value;
		}
		
		for (int i = 0; i < n2.length(); i++) {
			byte value = (byte) Character.getNumericValue(n2.charAt(i));
			if(value < 0) {
				String message = "Given number (" + n2 + ") contains a non digit character : " + n2.charAt(i) + " at " + i;
//				throw new ParseException(message, 1);
				throw new RuntimeException(message);
			}
			n2digits[i] = value;
		}
		
		byte[] digits = new byte[n1.length() + n2.length()];
		
		for (int i = n1digits.length - 1 ; i >= 0 ; i--) {
			for (int j = n2digits.length - 1; j >= 0 ; j--) {
				addDigit(digits, (n1digits.length - 1 - i) + (n2digits.length - 1 - j), (byte) (n1digits[i]*n2digits[j]));
			}
		}
		
		StringBuilder result = new StringBuilder();
		
		boolean skipZero = true;
		for(byte b : digits) {
			if(skipZero)  {
				if(b != 0) {
					skipZero = false;
				} else {
					continue;
				}
			} 
			result.append(b);
		}
		
		return result.toString();
	}
	
	/**
	 * Adds byte to certain digit in given array in base 10.
	 * 
	 * @param sum current sum of numbers, where smallest powers of base are at end and highest at start
	 * @param basePower shows position in array added to, ie 1 -> 0, 10 -> 1 and 100 -> 2
	 * @param toAdd number to add to that position
	 */
	private static void addDigit(byte[] sum, int basePower, byte toAdd) {
		
		int index = sum.length - basePower - 1;
		
		byte newValue = (byte) (sum[index] + toAdd);

		//To get new base change these "10"s
		byte carry = (byte) (newValue/10);
		byte reminder = (byte) (newValue%10);

		sum[index] = reminder;
		
		if(carry != 0) addDigit(sum, basePower + 1, carry);
	}
	
	/**
	 * Finds factorial value of given number.
	 * @param n number of which factorial to find
	 * @return factorial value
	 */
	public static BigInteger factorial(BigInteger n) {
		BigInteger result = BigInteger.ONE;
		for (int i = 1; i <= n.intValue(); i++) {
			result = result.multiply(new BigInteger(i + ""));
		}
		return result;
	}
	
	
	
	/**
	 * Finds the sum of all the proper divisors of given number.
	 * @param target number of which divisors to find
	 * @return sum of divisors
	 * @see <a href="http://stackoverflow.com/a/18836978/4015280">Stackoverflow question</a>
	 * @see <a href="https://en.wikipedia.org/wiki/Divisor_function#Properties">Algorithm wikipedia page</a>
	 * @see <a href="http://planetmath.org/FormulaForSumOfDivisors">Algorithm explanation</a>
	 */
	public static int divisorsSum(int target)	{
		
		if(target <= 0) return 0;
		
	    int t = target;
	    int result = 1;

	    //  Handle two specially.
	    {
	        int p = leastPower(2, t);
	        result *= p-1;
	        t /= p/2;
	    }

	    //  Handle odd factors.
	    for (int i = 3; i*i <= t; i += 2) {
	        int p = leastPower(i, t);
	        result *= (p-1) / (i-1);
	        t /= p/i;
	    }
	    //  At this point, t must be one or prime.
	    if (1 < t) {
	    	result *= 1+t;
	    }

	    return result - target;
	}
	
	private static int leastPower(int a, int x)
	{
	    int b = a;
	    while (x % b == 0)
	        b *= a;
	    return b;
	}
}
