package org.openclover.samples.parameterized.junit4.example;

import java.math.BigDecimal;

/**
 * A fixed implementation of the a^2 ;-)
 */
public class Square {

	static int compute(int a) {
		return a * a;
	}

	static int compute(double a) {
		return (int) Math.pow(a, 2);
	}
	 
	static int compute(BigDecimal base) {
       return base.pow(2).intValue();
    }

}
