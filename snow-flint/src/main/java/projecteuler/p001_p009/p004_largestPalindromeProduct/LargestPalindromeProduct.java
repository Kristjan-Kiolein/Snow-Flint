package projecteuler.p001_p009.p004_largestPalindromeProduct;

import java.util.Arrays;

/**
 * <b>Largest palindrome product</b><br>
 * A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91 � 99.<br>
 * <i>Find the largest palindrome made from the product of two 3-digit numbers.</i><br>
 */
public class LargestPalindromeProduct {

	public static void main(String[] args) {
		System.out.println(solve());
	}
	
	
	private static int solve() {
		
		int[] num = findPalindrome();
		System.out.println(Arrays.toString(num));
		return num[0]*num[1];
	}
	
	private static int[] findPalindrome(){
		
		int start = 999;
		int end = 99;
		int palindrome = 0;
		int[] factors = {0,0}; 
		
		for(int i = start; i > end; i--) {
			for(int j = start; j > end; j--) {
				int number = i*j;
				if(isPalindrome(number)) {
					if(number > palindrome) {
						palindrome = number;
						factors[0] = i;
						factors[1] = j;
					}
				}
			}
		}
		return factors;
	}
	
	
	private static boolean isPalindrome(int number) {
		String n = number + "";
		
		for(int i = 0; i < n.length()/2; i++) {
			if(n.charAt(i) != n.charAt(n.length() - i -1)) return false;
		}
		return true;
	}
	
	
	
	
	
	
}
