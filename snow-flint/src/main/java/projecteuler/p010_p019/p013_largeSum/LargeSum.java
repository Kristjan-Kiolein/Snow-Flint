package projecteuler.p010_p019.p013_largeSum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>Large sum</b><br>
 * Work out the first ten digits of the sum of the following one-hundred 50-digit numbers.<br>
 * Digits in "digits.txt" file.<br>
 */
public class LargeSum {

	
	public static void main(String[] args) {
		List<String> numbers = readNumbers(Paths.get("src/main/java/projecteuler/p13_largeSum", "numbers.txt"));
		long t = System.currentTimeMillis();
		solve(numbers);
		System.out.println(System.currentTimeMillis() - t);
	}
	
	
	
	public static String solve(List<String> numbers) {
		
		//Make array with maximum length of resulting sum
		//Assuming all numbers are same length (or smaller than first)
		byte[] sum = new byte[numbers.get(0).length() + (numbers.size() + "").length()];
		
		//Iterate over all the digits in every number, O(n*k)
		for(String n : numbers) {
			for (int i = n.length() - 1; i >= 0 ; i--) {
				int toAdd = Character.getNumericValue(n.charAt(i));
				addDigit(sum, i, n.length(), toAdd);
			}
		}
		
		StringBuilder sb = new StringBuilder(sum.length);
		for(byte c : sum) {
			sb.append(c);
		}
		
		return sb.toString();
	}

	private static void addDigit(byte[] sum, int charIndex, int numberLength, int toAdd) {
		
		//So the result wouldn't come backwards
		//We are reading smaller numbers first, so put them at end of the array.
		byte index = (byte) (sum.length + charIndex - numberLength);
		
		byte newValue = (byte) (sum[index] + toAdd);

		byte carry = (byte) (newValue/10);
		byte reminder = (byte) (newValue%10);

		sum[index] = reminder;
		
		if(carry != 0) addDigit(sum, charIndex - 1, numberLength, carry);
	}
	
	

	
	
	
	
	
	
	
	public static List<String> readNumbers(Path file) {
		List<String> numbers = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file.toString()))) {
			for (String line; (line = br.readLine()) != null;) {
				numbers.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return numbers;
	}
}
