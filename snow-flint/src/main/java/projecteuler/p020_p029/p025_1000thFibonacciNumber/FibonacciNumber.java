package projecteuler.p020_p029.p025_1000thFibonacciNumber;

import java.math.BigInteger;

/**
 * <h1>1000-digit Fibonacci number</h1>
 * <br>
 * The Fibonacci sequence is defined by the recurrence relation:<br>
 * <br>
 * Fn = Fn−1 + Fn−2, where F1 = 1 and F2 = 1.<br>
 * Hence the first 12 terms will be:<br>
 * <br>
 * F1 = 1<br>
 * F2 = 1<br>
 * F3 = 2<br>
 * F4 = 3<br>
 * F5 = 5<br>
 * F6 = 8<br>
 * F7 = 13<br>
 * F8 = 21<br>
 * F9 = 34<br>
 * F10 = 55<br>
 * F11 = 89<br>
 * F12 = 144<br>
 * The 12th term, F12, is the first term to contain three digits.<br>
 * <br> 
 * <i>What is the index of the first term in the Fibonacci sequence to contain 1000 digits?</i>
 */
public class FibonacciNumber {

	public static void main(String[] args) {
		int index = solve(1000);
		System.out.println(index);
	}

	private static int solve(int digits) {
		
		BigInteger last = new BigInteger("1");
		BigInteger current = new BigInteger("1");
		Integer index = 2;
		
		while(current.toString().length() < digits) {
			index++;
			BigInteger next = current.add(last);
			last = current;
			current = next;
		}
		return index;
	}

	/* Using formula :
	    Saying that a number contains 1000 digits is the same as
		saying that it's greater than 10**999.
		
		The nth Fibonacci number is [phi**n / sqrt(5)], where the
		brackets denote "nearest integer".
		
		So we need phi**n/sqrt(5) > 10**999
		
		n * log(phi) - log(5)/2 > 999 * log(10)
		
		n * log(phi) > 999 * log(10) + log(5)/2
		n > (999 * log(10) + log(5) / 2) / log(phi)
		
		A handheld calculator shows the right hand side to be
		4781.8593, so 4782 is the first integer n with the
		desired property.
		
		Why bother with a computer on this one?
	 */
	
}
