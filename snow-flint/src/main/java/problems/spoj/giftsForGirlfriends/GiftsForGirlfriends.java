package problems.spoj.giftsForGirlfriends;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h1>BEHAPPY - Be Awesome As Barney Stinson</h1><br>
 *
 * Barney Stinson ;) is way too flirty. He has many girlfriends and he wants to keep all of them happy. He has M girlfriends.<br>
 * He bought N gifts for them. Now he knows that some girlfriends need more gifts and some need less.<br>
 * So he decided that he will give atleast Ai gifts and at most Bi gifts to his ith girlfriend.<br>
 * He has to give away all the N gifts. Tell us in how many different ways he can do this.<br>
 * <br>
 * INPUT:<br>
 * For each test case, first line contains two integers M and N, then follows M lines each having two integers Ai and Bi (1<=i<=M).<br>
 * Input ends with M and N both equal to 0 and that case should not be processed.<br>
 * <br>
 * OUTPUT:<br>
 * For each test case, output the number of different ways in which he can distribute those gifts in a single line.<br>
 * <br>
 * Example:<br>
 * <br>
 * 	Input:<br>
 * 	3 5<br>
 * 	0 1<br>
 * 	1 3<br>
 * 	1 4<br>
 * 	0 0<br>
 * 	<br>
 * 	Output:<br>
 * 	6<br>
 * 	<br>
 * Explanation: He can distribute 5 gifts in his 3 girlfriends in 6 different ways as follows (0 1 4), (0 2 3), (0 3 2), (1 1 3), (1 2 2), (1 3 1).<br>
 * <br>
 * Constraints: 1<=M<=20, 1<=N<=100, 0<=Ai,Bi,<=100<br>
 * 
 * http://www.spoj.com/problems/BEHAPPY/
 */
public class GiftsForGirlfriends {
	
	
	public static void main(String[] args) {
		if(args != null) {
//			int[][] values = new int[args.length - 1][2];
//			values[0][0] = Character.getNumericValue(args[0].charAt(0));
//			values[0][1] = Character.getNumericValue(args[0].charAt(1));
//			for(int i = 1; i < args.length - 1; i++) {
//				values[i][0] = Character.getNumericValue(args[i].charAt(0));
//				values[i][1] = Character.getNumericValue(args[i].charAt(1));
//			}
//			solve(new int[][]{{3,5}, {0,1}, {1,3}, {1,4}, {0,0}});
//			solve(values);
			solve(generateData());
		} else {
			System.out.println("No args given");
		}
	}
	
	private static void solve(int[][] values) {
		
		int nrOfGirls = values[0][0];
		int nrOfGifts = values[0][1];
		int combinations = 0;
		List<Integer> possibleGifts = new ArrayList<>();
		
		for(int i = 0; i < nrOfGirls; i++) {
			List<Integer> gifts = new ArrayList<>();
			for(int j = values[i + 1][0]; j <= values[i + 1][1]; j++) {
				gifts.add(j);
			}
			
			if(possibleGifts.isEmpty()) {
				possibleGifts = gifts;
			} else {
				List<Integer> result = new ArrayList<>();
				for(int m = 0; m < possibleGifts.size(); m++) {
					for (int n = 0; n < gifts.size(); n++) {
						int e = possibleGifts.get(m) + gifts.get(n);
						if(e <= nrOfGifts) {
							result.add(e);
						}
					}
				}
				possibleGifts = result;
			}
		}
		
		for(Integer i : possibleGifts) {
			if(i == nrOfGifts) combinations++; 
		}
		
		System.out.println(combinations);
		
	}
	
	/*
	private static List<Integer> expandBrackets(List<Integer> first, List<Integer> second) {
		List<Integer> result = new ArrayList<>();
		
		if(first == null || first.isEmpty())return second;
		if(second == null|| second.isEmpty()) return first;
		
		for(int i = 0; i < first.size(); i++) {
			for (int j = 0; j < second.size(); j++) {
				result.add(first.get(i) + second.get(j));
			}
		}
		return result;
	}
	*/
	
	private static int[][] generateData() {
		int girls = 5;
		int gifts = 6;
		int[][] data = new int[girls + 1][2];
		data[0][0] = girls;
		data[0][1] = gifts;
		
		Random r = new Random();
		r.setSeed(1234313L);
		
		for(int i = 1; i < girls; i++) {
			int bound = gifts/girls;
			data[i][0] = r.nextInt(bound*2);
			data[i][1] = bound*2 + r.nextInt(bound);
		}
		return data;
	}
	
}









































