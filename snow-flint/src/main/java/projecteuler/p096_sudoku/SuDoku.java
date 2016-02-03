package projecteuler.p096_sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.SetUtils.SetView;

import lib.datastructures.KArrays;

/**
 * <b>Problem 96</b>
 * <br><br>
 * Su Doku (Japanese meaning number place) is the name given to a popular puzzle concept.
 * Its origin is unclear, but credit must be attributed to Leonhard Euler who invented a similar,
 * and much more difficult, puzzle idea called Latin Squares. The objective of Su Doku puzzles,
 * however, is to replace the blanks (or zeros) in a 9 by 9 grid in such that each row, column,
 * and 3 by 3 box contains each of the digits 1 to 9. Below is an example of a typical starting
 * puzzle grid and its solution grid.
 *<br><br>
 *<pre>
 * 0 0 3   0 0 8   0 0 2			4 8 3   5 4 8   3 7 2<br>   
 * 9 0 0   7 0 0   8 0 0			9 6 7   7 2 9   8 1 4<br>    
 * 0 0 1   0 0 6   0 0 5			2 5 1   1 3 6   6 9 5<br> 
 * 		    	       	 			                     <br>
 * 0 2 0   1 0 2   6 0 9			9 2 1   1 3 2   6 8 9<br>
 * 3 0 5   0 0 0   2 0 3			3 4 5   5 6 4   2 5 3<br>
 * 8 0 6   7 0 8   0 1 0			8 7 6   7 9 8   4 1 7<br>
 * 		    	       	 			                     <br>
 * 6 0 0   9 0 0   5 0 0			6 5 7   9 7 6   5 1 4<br>
 * 0 0 1   0 0 8   0 0 9			8 2 1   1 3 8   7 6 9<br>
 * 4 0 0   2 0 0   3 0 0			4 9 3   2 4 5   3 8 2<br>
 *</pre>
 *<br>
 * A well constructed Su Doku puzzle has a unique solution and can be solved by logic, although it may be
 * necessary to employ "guess and test" methods in order to eliminate options (there is much contested opinion over this).
 * The complexity of the search determines the difficulty of the puzzle;
 * the example above is considered easy because it can be solved by straight forward direct deduction.
 * <br><br>
 * The 6K text file, sudoku.txt contains fifty different Su Doku puzzles ranging in difficulty,
 * but all with unique solutions (the first puzzle in the file is the example above).
 * <br><br>
 * <i>By solving all fifty puzzles find the sum of the 3-digit numbers found in the top left corner of each solution grid;
 * for example, 483 is the 3-digit number found in the top left corner of the solution grid above.</i>
 */
@SuppressWarnings({"unused", "rawtypes", "unchecked"})
public class SuDoku {
	
	private void unused(){};
	private static final int BOARD_SIZE = 9;
	private static final int NR_OF_SECTIONS = 3;
	private static final Set<Integer> VALUES = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
	private static final Comparator<Integer> REVERSE_ORDER = new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2.compareTo(o1);
		}
	};
	
	
	
	private List<Integer[][]> sudokus;
	private List<Integer[][]> solutions;
	
	public SuDoku(Path file) {
		sudokus = readSuDokus(file);
		solutions = new ArrayList<>();
	}
	
	
	public void solve() {
		
//		for(Integer[][] sudoku : sudokus) {
//			Integer [][] board = KArrays.copyOf(sudoku, Integer.class);
//			solutions.add(solve(board));
//		}
		
		int i = 3;
		printBoard(sudokus.get(i));
		printBoard(solve(sudokus.get(i)));
		
		
//		for (int i = 0; i < solutions.size(); i++) {
//			Integer[][] sudoku = solutions.get(i);
//						System.out.println("\nSolution : ");
//						System.out.println("Solution found for board " + i + " : " + !containsZero(sudoku));
//		}
	}
	

	private Integer[][] solve(Integer[][] board) {
		if(board == null) return null;
		Set[][] takenMoves = getTakenMovesAsSet(board);
		Map<Integer, Map<int[], Set<Integer>>> groupedTakenMoves = groupTakenMoves(takenMoves);
		Integer[][] result = board;
		
		if(!groupedTakenMoves.isEmpty()) {
			for(Map<int[], Set<Integer>> taken : groupedTakenMoves.values()){
				for(Entry<int[], Set<Integer>> entry : taken.entrySet()) {
					SetView<Integer> possibleMoves = SetUtils.difference(VALUES, entry.getValue());
					for(Integer move : possibleMoves){
						Integer[][] boardCopy = KArrays.copyOf(board, Integer.class);
						boardCopy[entry.getKey()[0]][entry.getKey()[1]] = move;
						result = solve(boardCopy);
						if (result != null && !containsZero(result)) return result;
					}
				}
			}
			
		}
		return result;
	}
	
	private TreeMap<Integer, Map<int[], Set<Integer>>> groupTakenMoves(Set[][] takenMoves) {
		TreeMap<Integer, Map<int[], Set<Integer>>> bestMoves = new TreeMap<>(REVERSE_ORDER);
		for (int i = 0; i < takenMoves.length; i++) {
			for (int j = 0; j < takenMoves.length; j++) {
				int size = takenMoves[i][j].size();
				if(size == 0) continue;
				if(bestMoves.get(size) == null) {
					bestMoves.put(size, new HashMap<>());
				}
				bestMoves.get(size).put(new int[]{i, j}, takenMoves[i][j]);
			}
		}
		return bestMoves;
	}



	private Set[][] getTakenMovesAsSet(Integer[][] board) {
		Set[][] takenMoves = newHashArray(BOARD_SIZE);
		//For each board square
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				//Get moves that can't be made there
				if(board[i][j] == 0) {
					for(int ii = 0; ii < BOARD_SIZE; ii++) {
						takenMoves[i][j].add(board[ii][j]);
					}
					for(int jj = 0; jj < BOARD_SIZE; jj++) {
						takenMoves[i][j].add(board[i][jj]);
					}
					//Those not in same row or column but in same section
					for(int ij = 1; ij < NR_OF_SECTIONS; ij++){
						int x = (i/NR_OF_SECTIONS)*NR_OF_SECTIONS + (i + ij)%3;
						for(int ji = 1; ji < NR_OF_SECTIONS; ji++) {
							int y = (j/NR_OF_SECTIONS)*NR_OF_SECTIONS + (j + ji)%3;
							takenMoves[i][j].add(board[x][y]);
						}
					}
				}
			}
		}
		return takenMoves;
	}

	private Set[][] newHashArray(int size) {
		Set[][] hashArray = new HashSet[size][size];
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				hashArray[i][j] = new HashSet<Integer>();
			}
		}
		return hashArray;
	}
	
	private Boolean[][][] getTakenMovesAsArray(Integer[][] board) {
		Boolean[][][] takenMoves = new Boolean[BOARD_SIZE][BOARD_SIZE][BOARD_SIZE + 1];
		//For each board square
		for(int i = 0; i < BOARD_SIZE; i++) {
			for(int j = 0; j < BOARD_SIZE; j++) {
				//Get moves that can't be made there
				if(board[i][j] == 0) {
					for(int ii = 0; ii < BOARD_SIZE; ii++) {
						takenMoves[i][j][board[ii][j]] = true;
					}
					for(int jj = 0; jj < BOARD_SIZE; jj++) {
						takenMoves[i][j][board[i][jj]] = true;
					}
					//Those not in same row or column but in same section
					for(int ij = 1; ij < NR_OF_SECTIONS; ij++){
						int x = (i/NR_OF_SECTIONS)*NR_OF_SECTIONS + (i + ij)%3;
						for(int ji = 1; ji < NR_OF_SECTIONS; ji++) {
							int y = (j/NR_OF_SECTIONS)*NR_OF_SECTIONS + (j + ji)%3;
							takenMoves[i][j][board[x][y]] = true;
						}
					}
				}
			}
		}
		return takenMoves;
	}
	

	private List<Integer[][]> readSuDokus(Path file) {
		List<Integer[][]> sudokus = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new FileReader(file.toString()))) {
			Integer[][] current = null;
			int lineIndex = 0;
		    for(String line; (line = br.readLine()) != null; ) {
		    	if(line.startsWith("Grid")) {
		    		if(current != null) sudokus.add(current);
		    		lineIndex = 0;
		    		current = new Integer[BOARD_SIZE][BOARD_SIZE];
		    	} else {
		    		for (int i = 0; i < line.length(); i++){
		    		    current[lineIndex][i] = Character.getNumericValue(line.charAt(i));
		    		}
		    		lineIndex++;
		    	}
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sudokus;
	}

	private void printBoard(Integer[][] board) {
		if(board == null){
			System.out.print("No board");
			return;
		}
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j] + " ");
				if((j + 1) % NR_OF_SECTIONS == 0) System.out.print(" ");
			}
			System.out.println();
			if((i + 1) % NR_OF_SECTIONS == 0) System.out.println();
		}
	}
	
	private boolean containsZero(Integer[][] sudoku) {
		if(sudoku == null) return true;
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				if(sudoku[i][j] == 0)
					return true;
			}
		}
		return false;
	}
}
