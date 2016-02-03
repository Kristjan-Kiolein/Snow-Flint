package projecteuler.p020_p029.p022_namesScores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h1>Names scores</h1>
 * <br>
 * Using names.txt , a 46K text file containing over five-thousand first names,
 * begin by sorting it into alphabetical order. Then working out the alphabetical value for each name,
 * multiply this value by its alphabetical position in the list to obtain a name score.<br>
 * <br>
 * For example, when the list is sorted into alphabetical order,
 * COLIN, which is worth 3 + 15 + 12 + 9 + 14 = 53, is the 938th name in the list.
 * So, COLIN would obtain a score of 938 × 53 = 49714.<br>
 * <br>
 * <i>What is the total of all the name scores in the file?</i><br>
 */
public class NamesScores {

	private static final List<String> ALPHABET = Arrays.asList(	"A", "B", "C", "D", "E", "F", "G", "H", "I",
																"J", "K", "L", "M", "N", "O", "P", "Q", "R",
																"S", "T", "U", "V", "W", "X", "Y", "Z");
	
	
	
	public static void main(String[] args) {
		Path names = Paths.get("src/main/java/projecteuler/p022_namesScores", "p022_names.txt");
		List<String> unsortedNames = readGrid(names);
//		System.out.println(unsortedNames);
		long score = solve(unsortedNames);
		System.out.println(score);
	}
	
	private static long solve(List<String> names) {
		
		Collections.sort(names);
		Map<String,Integer> letterCount = new HashMap<>();
		
		for (String letter : ALPHABET) {
			letterCount.put(letter, 0);
		}
		
		long sum = 0;
		
		for (int i = 0; i < names.size(); i++) {
			int localSum = 0;
			for(int j = 0; j < names.get(i).length(); j++) {
				localSum += ALPHABET.indexOf(Character.toString(names.get(i).charAt(j))) + 1;
			}
			sum += localSum*(i+1);
		}
		
		return sum;
	}





	private static List<String> readGrid(Path file) {
		List<String> unsortedNames = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file.toString()))) {
			for (String line; (line = br.readLine()) != null;) {
				String[] names = line.split(",");
				unsortedNames.addAll(Arrays.asList(names));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		unsortedNames.replaceAll(x -> x.replaceAll("\"", ""));
		
		return unsortedNames;
	}
}
