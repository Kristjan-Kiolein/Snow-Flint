package lib.math;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class KMathTest {

	private static final int NR_OF_SQR_TESTS = 30;
	private static final int NR_OF_ALL_TESTS = 500;
	
	
	public static class IsPerfectSquareTest {
		
		@Test
		public void perfectSquares() {
			for(int i = 0; i < NR_OF_SQR_TESTS; i++) {
				Assert.assertTrue(KMath.isPerfectSquare(i*i));
			}
		}
		
		@Test
		public void allNumbers() {
			for(int i = 0; i < NR_OF_ALL_TESTS; i++) {
				if(!KMath.isPerfectSquare(i))
					Assert.assertFalse(KMath.isPerfectSquare(i));
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static class IsPerfectSquareSimpleTest {
		
		@Test
		public void perfectSquares() {
			for(int i = 0; i < NR_OF_SQR_TESTS; i++) {
				Assert.assertTrue(KMath.isPerfectSquareSimple(i*i));
			}
		}
		
		@Test
		public void allNumbers() {
			for(int i = 0; i < NR_OF_ALL_TESTS; i++) {
				if(!KMath.isPerfectSquare(i))
					Assert.assertFalse(KMath.isPerfectSquareSimple(i));
			}
		}
	}
	
}
