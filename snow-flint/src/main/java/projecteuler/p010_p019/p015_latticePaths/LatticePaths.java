package projecteuler.p010_p019.p015_latticePaths;

import java.math.BigInteger;

import lib.math.KMath;

/**
 * <h1>Lattice paths</h1><br>
 *
 * Starting in the top left corner of a 2×2 grid, and only being able to move to the right and down, there are exactly 6 routes to the bottom right corner.<br>
 *<br> 
 * R - right, D - down<br>
 * RRDD, RDRD, RDDR,DRRD, DRDR and DDRR<br>
 * <br>
 * <i>How many such routes are there through a 20×20 grid?</i><br>
 *
 */
public class LatticePaths {

	public static void main(String[] args) {
		
		int size = 20;
		String result = solve(size);
		System.out.println(result);

	}
	
	
	/**
	 * We must make n moves to right and n moves down.
	 * All combinations of those moves will be all the possible routes.
	 * @param size size of grid
	 * @return number of possible routes
	 */
	private static String solve(int size) {
		
		
		BigInteger result = KMath.factorial(new BigInteger((size + size) + ""));
		result = result.divide(KMath.factorial(new BigInteger(size + "")));
		result = result.divide(KMath.factorial(new BigInteger(size + "")));

		return result.toString();
				
	}
	

}
