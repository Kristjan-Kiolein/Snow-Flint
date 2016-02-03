package projecteuler.p009_pythagoreanTriplet;

import lib.math.KMath;

/**
 *  <b>Special Pythagorean triplet</b><br>
 *	A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,<br>
 *	a<sup>2</sup> + b<sup>2</sup> = c<sup>2</sup><br>
 *	For example, 3<sup>2</sup> + 4<sup>2</sup> = 9 + 16 = 25 = 5<sup>2</sup>.<br>
 *	There exists exactly one Pythagorean triplet for which a + b + c = 1000.<br>
 *	Find the product <i>abc</i>.<br>
 */
public class SpecialPythagoreanTriplet {

	
	
	public static int solve() {
		int[] abc = findAbcBetter();
		return abc[0]*abc[1]*abc[2];
	}

	public static int[] findAbc() {
		int targetValue = 1000;
		
		for (int a = 1; a < targetValue; a++) {
			for (int b = a; b < targetValue; b++) {
				int cSquare = a*a + b*b;
				if(KMath.isPerfectSquare(cSquare)) {
					int c = (int) Math.sqrt(cSquare);
					if(a + b + c == targetValue) {
						return new int[] {a, b, c};
					}
				}
			}
		}
		return null;
	}
	
	public static int[] findAbcBetter() {
		int targetValue = 1000;
		
		for (int a = 1; a < targetValue; a++) {
			for (int b = a; b < targetValue; b++) {
				int c = targetValue - a - b;
				if((a*a + b*b) == c*c) {
					return new int[] {a, b, c};
				}
			}
		}
		return null;
	}
	
	
}
