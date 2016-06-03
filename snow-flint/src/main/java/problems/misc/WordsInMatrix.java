package problems.misc;

import java.util.*;

/**
 * Find how many times word x can be formed using connected letters (specified in matrix B) in matrix A.
 * ie word RADAR in matrix
 * [R, A, D, A, R]
 * [A, A, -, A, A]
 * [D, -, D, -, D]
 * [A, A, -, A, A]
 * [R, A, D, A, R]
 * <p>
 * where the connections are marked as following for x :
 * 0 1 2
 * 7 x 3
 * 6 5 4
 * <p>
 * connections matrix:
 * [[3, 4, 5], [7, 3], [7, 6, 3, 4], [7, 3], [7, 6, 5]]
 * [[1, 5], [0, 2, 6, 4], [], [0, 2, 6, 4], [1, 5]]
 * [[1, 2, 4, 5], [], [0, 2, 6, 4], [], [0, 1, 6, 5]]
 * [[1, 5], [0, 2, 6, 4], [], [0, 2, 6, 4], [1, 5]]
 * [[7, 6, 5], [7, 3], [0, 2, 3, 7], [7, 3], [3, 4, 5]]
 *
 * Anwser : 80 if one letter can be used multiple times
 */
public class WordsInMatrix {


	private static final String WORD = "RADAR";
	private static final char NULL = '-';
	private static final char[][] WORD_MATRIX = {
			{'R', 'A', 'D', 'A', 'R'},
			{'A', 'A', NULL, 'A', 'A'},
			{'D', NULL, 'D', NULL, 'D'},
			{'A', 'A', NULL, 'A', 'A'},
			{'R', 'A', 'D', 'A', 'R'}};

	private static final int[][][] CONNECTIONS = {
			{{3, 4, 5}, {7, 3}, {7, 6, 3, 4}, {7, 3}, {7, 6, 5}},
			{{1, 5}, {0, 2, 6, 4}, {}, {0, 2, 6, 4}, {1, 5}},
			{{1, 2, 4, 5}, {}, {0, 2, 6, 4}, {}, {0, 1, 6, 5}},
			{{1, 5}, {0, 2, 6, 4}, {}, {0, 2, 6, 4}, {1, 5}},
			{{1,2,3}, {7, 3}, {0, 2, 3, 7}, {7, 3}, {0, 1, 7}}};

	private static Map<Integer, int[]> CONNECTION_DIRECTION;

	static {
		CONNECTION_DIRECTION = new HashMap<>();
		CONNECTION_DIRECTION.put(0, new int[]{-1, -1});
		CONNECTION_DIRECTION.put(1, new int[]{0, -1});
		CONNECTION_DIRECTION.put(2, new int[]{1, -1});
		CONNECTION_DIRECTION.put(3, new int[]{1, 0});
		CONNECTION_DIRECTION.put(4, new int[]{1, 1});
		CONNECTION_DIRECTION.put(5, new int[]{0, 1});
		CONNECTION_DIRECTION.put(6, new int[]{-1, 1});
		CONNECTION_DIRECTION.put(7, new int[]{-1, 0});
	}

	public static void main(String[] args) {
		WordsInMatrix wordsInMatrix = new WordsInMatrix();
		int matches = wordsInMatrix.countMatches(WORD_MATRIX, CONNECTIONS, WORD, false);
		System.out.println("Got " + matches + " matches");
	}

	private int countMatches(char[][] wordMatrix, int[][][] connections, String word, boolean allowMultipleVisits) {
		int matches = 0;
		int colLen = wordMatrix.length;
		int rowLen = wordMatrix[0].length;

		for (int y = 0; y < colLen; y++) {
			for (int x = 0; x < rowLen; x++) {
				matches += countMatches(wordMatrix, connections, word, x, y, 0, allowMultipleVisits, new HashSet<>());
			}
		}
		return matches;
	}

	private int countMatches(char[][] wordMatrix, int[][][] connections, String word, int x, int y, int charAt, boolean allowMultipleVisits , Set<String> visited) {

		int matches = 0;
		String identifier = x + " " + y;
		visited.add(identifier);

		if (word.charAt(charAt) == wordMatrix[y][x]) {
			if (charAt == word.length() - 1) {
				matches = 1;
			} else {
				charAt++;
				for (int connection : connections[y][x]) {
					int[] direction = CONNECTION_DIRECTION.get(connection);
					int newX = direction[0] + x;
					int newY = direction[1] + y;
					if (allowMultipleVisits || !visited.contains(newX + " " + newY)) {
						matches += countMatches(wordMatrix, connections, word, newX, newY, charAt, allowMultipleVisits, visited);
					}
				}
			}
		}
		visited.remove(identifier);
		return matches;
	}

}
