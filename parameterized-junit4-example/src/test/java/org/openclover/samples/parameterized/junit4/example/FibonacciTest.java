package org.openclover.samples.parameterized.junit4.example;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class FibonacciTest {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
        	 {0, 0},
        	 {1, 1},
             {2, 1},
             {3, 2},
             {4, 3},
             {5, 5},
             {6, 8},
             {7, 13},
             {8, 20}, // << error 
             {9, 34},
             {10, 55}
        });
    }

	private int input;
	private int expected;

	public FibonacciTest(int input, int expected) {
		this.input = input;
		this.expected = expected;
	}

	@Test
	public void testFibonacci() {
		assertEquals(expected, new Fibonacci().fibonacci(input));
	}
	
	@Test
	public void testFibonacciRecursive() {
		assertEquals(expected, new Fibonacci().fibonacciRecursive(input));
	}
}
