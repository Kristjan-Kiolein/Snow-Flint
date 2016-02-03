package projecteuler.p014_longestCollatzSequence;

/**
 * 	<b>Longest Collatz sequence</b><br>
 *  <br>
 *  The following iterative sequence is defined for the set of positive integers:<br>
 *	<br>
 *	n → n/2 (n is even)<br>
 *	n → 3n + 1 (n is odd)<br>
 *	<br>
 *	Using the rule above and starting with 13, we generate the following sequence:<br>
 *	<br>
 *	13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1<br>
 *	It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms. Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.<br>
 *	<br>
 *  <i>Which starting number, under one million, produces the longest chain?</i><br>
 *	<br>
 *  <b>NOTE<b/>: Once the chain starts the terms are allowed to go above one million.<br>
 *
 */
public class LongestCollatzSequence {

	public static void main(String[] args) {
		
		int highestNumber = 9999999;
		System.out.println("Max number : " + highestNumber);
		
		long t = System.currentTimeMillis();
		System.out.println("Length : " + solve(highestNumber));
		System.out.println("Took " + (System.currentTimeMillis() - t) + "ms");
		
		
	}
	
	
	private static int solve(int highestNumber) {
		
		int longestChain = 0;
		int longestStart = 0;
		int[] precalculated = new int[highestNumber];
		
		for(int i = 1; i < highestNumber; i++) {
			int length = sequenceLength(i, precalculated);
			precalculated[i] = length;
			if(length > longestChain) {
				longestChain = length;
				longestStart = i;
			}
		}
		System.out.println("Longest : " + longestStart);
		return longestChain;
	}
	
	
	private static int sequenceLength(int start, int[] preCalculated) {
		
		int length = 0;
		long current = start;
		
		while(current != 1) {
			if(current >= start) {
				length++;
				if(current%2 == 0) {
					current /= 2;
				} else {
					current = current*3 + 1;
				}
			} else {
				length += preCalculated[(int) current];
				return length;
			}
			
		}
		
		return length + 1;
	}
	
}
