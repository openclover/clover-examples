package org.openclover.samples.parameterized.junit4.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SquareTest {
	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			{ BigDecimal.valueOf(-3), 9 },
			{ -2.0d, 4 }, { -1.0d, 1 }, { -0.0d, 0 }, 
			{ BigDecimal.ZERO, BigDecimal.ZERO.intValue() },
			{ 0, 0 }, { 1, 1 }, { 2, 4 },
			{ BigDecimal.valueOf(3), 9 },
				{ "Answer", 42 } });
	}

	protected Object input;
	protected int expected;

	public SquareTest(Object input, int expected) {
		this.input = input;
		this.expected = expected;
	}

	@Test
	public void test() {
		if (input instanceof Integer)
			assertEquals(expected, Square.compute(((Integer) input).intValue()));
		else if (input instanceof Double)
			assertEquals(expected, Square.compute(((Double) input).doubleValue()));
		else if (input instanceof BigDecimal)
			assertEquals(expected, Square.compute((BigDecimal) input));
		else
			fail("Unexpected Object " + input + " to expected value " + expected + " ... ");
	}
}
