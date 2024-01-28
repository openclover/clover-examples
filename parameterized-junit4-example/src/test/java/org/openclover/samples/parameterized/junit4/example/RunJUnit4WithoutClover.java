package org.openclover.samples.parameterized.junit4.example;

import org.junit.runner.JUnitCore;

/**
 * Execute JUnit test runner without Clover's test listener. Clover can still collect code
 * coverage, but test names do not have iteration numbers (parameterized tests).
 */
public class RunJUnit4WithoutClover {
    public static void main(String[] args) {
    	new JUnitCore().run(new Class[] { SquareTest.class, FibonacciTest.class });
    }
}
