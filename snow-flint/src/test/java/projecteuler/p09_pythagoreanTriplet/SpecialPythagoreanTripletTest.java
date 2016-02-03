package projecteuler.p09_pythagoreanTriplet;

import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import projecteuler.p001_p009.p009_pythagoreanTriplet.SpecialPythagoreanTriplet;

@RunWith(Enclosed.class)
public class SpecialPythagoreanTripletTest {

	private static final int ABC_SUM = 1000;
	private static final int ABC_PRODUCT= 31875000;
	
	public static class SolveTest {
		@Test
		public void isCorrectAnwser() {
			Assert.assertEquals(SpecialPythagoreanTriplet.solve(), ABC_PRODUCT);
		}
	}
	
	
	public static class FindABCTest {
		@Test
		public void isCorrectAnwser() {
			int[] abc = SpecialPythagoreanTriplet.findAbc();
			Assert.assertNotNull(abc);
			Assert.assertTrue(abc[0] < abc[1]);
			Assert.assertTrue(abc[1] < abc[2]);
			Assert.assertTrue(IntStream.of(abc).sum() == ABC_SUM);
		}
	}
	
	
	
	
}
