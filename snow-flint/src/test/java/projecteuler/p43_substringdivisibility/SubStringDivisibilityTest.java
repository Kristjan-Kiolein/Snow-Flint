package projecteuler.p43_substringdivisibility;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import projecteuler.p43_substringdivisibility.SubStringDivisibility;

@RunWith(Enclosed.class)
public class SubStringDivisibilityTest {

	public static final class Solve {

		SubStringDivisibility subStringDivisibility;

		@Before
		public void setUp() {
			subStringDivisibility = new SubStringDivisibility();
		}

		@Test
		public void correctResult() {
			Assert.assertTrue(subStringDivisibility.solve().equals(new BigInteger("16695334890")));
		}
	}

}
