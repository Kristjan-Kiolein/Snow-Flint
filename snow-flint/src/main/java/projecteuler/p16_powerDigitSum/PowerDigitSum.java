package projecteuler.p16_powerDigitSum;

import lib.math.KMath;

/**
 * <h1>Power digit sum</h1><br>
 * 2<sup>15</sup> = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.<br>
 *<br>
 * <i>What is the sum of the digits of the number 2<sup>1000</sup>?</i><br>
 *
 */
public class PowerDigitSum {

	
	
	public static void main(String[] args) {
		System.out.println(solve());
	}

	private static int solve() {

		int exponent = 1000;
		String powerOf = "2";
		String result = "1";
		int sum = 0;
		
		for (int i = 0; i < exponent; i++) {
			result = KMath.multiply(powerOf, result);
		}
		
		for (int i = 0; i < result.length(); i++) {
			sum += Character.getNumericValue(result.charAt(i));
		}
		
		
		return sum;
	}
}
