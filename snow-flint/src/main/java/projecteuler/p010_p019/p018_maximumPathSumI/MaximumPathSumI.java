package projecteuler.p010_p019.p018_maximumPathSumI;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <h1>Maximum path sum I</h1>
 * <br>
 * By starting at the top of the triangle below and moving to adjacent numbers on the row below, the maximum total from top to bottom is 23.<br>
 * <br>
 * <b>3</b><br>
 * <b>7</b> 4<br>
 * 2 <b>4</b> 6<br>
 * 8 5 <b>9</b> 3<br>
 * <br>
 * That is, 3 + 7 + 4 + 9 = 23.<br>
 * <br>
 * <i>Find the maximum total from top to bottom of the triangle below:</i><br>
 * <br>
 * 75<br>
 * 95 64<br>
 * 17 47 82<br>
 * 18 35 87 10<br>
 * 20 04 82 47 65<br>
 * 19 01 23 75 03 34<br>
 * 88 02 77 73 07 63 67<br>
 * 99 65 04 28 06 16 70 92<br>
 * 41 41 26 56 83 40 80 70 33<br>
 * 41 48 72 33 47 32 37 16 94 29<br>
 * 53 71 44 65 25 43 91 52 97 51 14<br>
 * 70 11 33 28 77 73 17 78 39 68 17 57<br>
 * 91 71 52 38 17 14 91 43 58 50 27 29 48<br>
 * 63 66 04 68 89 53 67 30 73 16 69 87 40 31<br>
 * 04 62 98 27 23 09 70 98 73 93 38 53 60 04 23<br>
 * <br>
 * NOTE: As there are only 16384 routes, it is possible to solve this problem by trying every route.<br>
 * However, Problem 67, is the same challenge with a triangle containing one-hundred rows;<br>
 * it cannot be solved by brute force, and requires a clever method! ;o)<br>
 *
 */
public class MaximumPathSumI {
	
	private static final int[][] TRIANGLE = {
			{75},
			{95,64},
			{17,47,82},
			{18,35,87,10},
			{20,04,82,47,65},
			{19,01,23,75,03,34},
			{88,02,77,73,07,63,67},
			{99,65,04,28,06,16,70,92},
			{41,41,26,56,83,40,80,70,33},
			{41,48,72,33,47,32,37,16,94,29},
			{53,71,44,65,25,43,91,52,97,51,14},
			{70,11,33,28,77,73,17,78,39,68,17,57},
			{91,71,52,38,17,14,91,43,58,50,27,29,48},
			{63,66,04,68,89,53,67,30,73,16,69,87,40,31},
			{04,62,98,27,23, 9,70,98,73,93,38,53,60,04,23}};
	
	private static final Comparator<Integer[]> PATH_COMPARATOR = new Comparator<Integer[]>() {
		@Override
		public int compare(Integer[] o1, Integer[] o2) {
			return o2[0].compareTo(o1[0]);
		}
	};
		

	public static void main(String[] args) {
		int maxValue = solve();
		System.out.println(maxValue);
	}

	/**
	 * Bottom up solution probably much faster	
	 */
	private static int solve() {
		
		//paths[0] - path length
		//paths[1] - level/height in TRIANGLE
		//paths[2] - index in current level/height in TRIANGLE
		PriorityQueue<Integer[]> paths = new PriorityQueue<>(PATH_COMPARATOR);

		int triangleHeight = TRIANGLE.length;
		int bestSum = 0;
		int maxElement = getMaxValue(TRIANGLE);
		int calls = 0;
		
		//First element
		paths.add(new Integer[]{75, 0, 0});
		
		while(!paths.isEmpty()) {
			calls++;
			Integer[] current = paths.poll();

			if(current[1] == triangleHeight - 1) {
				if(current[0] > bestSum) bestSum = current[0];
			} else {
				if(current[0] + maxElement*(triangleHeight - current[1]) > bestSum) {
					paths.add(new Integer[]{current[0] + TRIANGLE[current[1] + 1][current[2]], current[1] + 1, current[2]});
					paths.add(new Integer[]{current[0] + TRIANGLE[current[1] + 1][current[2] + 1], current[1] + 1, current[2] + 1});
				}
				
			}
		}
		System.out.println("calls : " + calls);
		return bestSum;
	}
	
	private static int getMaxValue(int[][] triangle) {
		int maxValue = 0;
		for (int i = 0; i < triangle.length; i++) {
			for (int j = 0; j < triangle[i].length; j++) {
				if(triangle[i][j] > maxValue) {
					maxValue = triangle[i][j];
				}
			}
		}
		return maxValue;
	}

}
