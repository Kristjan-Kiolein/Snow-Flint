package projecteuler.p43_substringdivisibility;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import projecteuler.p43_substringdivisibility.SubStringDivisibility;

public class SubStringDivisibilityTest {

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
