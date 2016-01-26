package projecteuler.p13_largeSum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <b>Large sum</b><br>
 * Work out the first ten digits of the sum of the following one-hundred 50-digit numbers.<br>
 * Digits in "digits.txt" file.<br>
 */
public class LargeSum {

	
	public static void main(String[] args) {
		List<String> numbers = readGrid(Paths.get("src/main/java/projecteuler/p13_largeSum", "numbers.txt"));
		solve(numbers);
	}
	
	private static void solve(List<String> numbers) {
		int[] sum = new int[numbers.get(0).length() + (numbers.size() + "").length()];
		
		for(String n : numbers) {
			for (int i = n.length() - 1; i >= 0 ; i--) {
				int toAdd = Character.getNumericValue(n.charAt(i));
				addDigit(sum, i, toAdd, n.length());
			}
		}
		
		
		System.out.println(Arrays.toString(sum));
		
	}

	private static void addDigit(int[] sum, int i, int toAdd, int n) {
		
		int newValue = sum[i] + toAdd;

		int carry = newValue/10;
		int reminder = newValue%10;

		sum[i] = reminder;
		
		if(carry != 0) addDigit(sum, i + 1, carry, n);
	}
	
	
	
	
	
	
	
	
	
	
	
	private static List<String> readGrid(Path file) {
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
