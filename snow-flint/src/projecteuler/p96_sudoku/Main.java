package projecteuler.p96_sudoku;

import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		SuDoku sudoku = new SuDoku(Paths.get("src/projecteuler/p96_sudoku", "sudoku.txt"));
		sudoku.solve();
		
	}

	
}
