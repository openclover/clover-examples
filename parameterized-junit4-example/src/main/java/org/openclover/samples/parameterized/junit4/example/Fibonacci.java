package org.openclover.samples.parameterized.junit4.example;

public class Fibonacci {

	/**
	 * @param n number
	 * @return The Fibonacci number to calc
	 */
	int fibonacciRecursive(int n) {
		if (n <= 0) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return 1;
		}
		if (n == 3 || n == 4) {
			return n - 1;
		}
		return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
	}

	/**
	 * @param n number
	 * @return The Fibonacci number to calc
	 */
	int fibonacci(int n) {
		if (n <= 0) {
			return 0;
		} else if (n == 1 || n == 2) {
			return 1;
		} else if (n == 3 || n == 4) {
			return n - 1;
		} else {
			int fib = 0;
			int prev1 = 2;
			int prev2 = 3;

			for (int i = 5; i <= n; i++) {
				fib = prev1 + prev2;
				prev1 = prev2;
				prev2 = fib;
			}

			return fib;
		}
	}

}
