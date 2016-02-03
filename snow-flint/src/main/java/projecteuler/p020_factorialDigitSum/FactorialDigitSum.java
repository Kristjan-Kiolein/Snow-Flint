package projecteuler.p020_factorialDigitSum;

import lib.math.KMath;

/**
 * <h1>Factorial digit sum</h1>
 * <br>
 * n! means n × (n − 1) × ... × 3 × 2 × 1<br>
 * <br>
 * For example, 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800,<br>
 * and the sum of the digits in the number 10! is 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.<br>
 * <br>
 * <i>Find the sum of the digits in the number 100!</i><br>
 */
public class FactorialDigitSum {

	public static void main(String[] args) {
		int sum = solve(100);
		System.out.println(sum);
	}

	private static int solve(int i) {
		
		String factorial = "1";
		int sum = 0;
		
		for (int j = 1; j < i; j++) {
			factorial = KMath.multiply(factorial, Integer.toString(j));
		}
		
		for (int j = 0; j < factorial.length(); j++) {
			sum += Character.getNumericValue(factorial.charAt(j));
		}
		
		return sum;
	}

}
