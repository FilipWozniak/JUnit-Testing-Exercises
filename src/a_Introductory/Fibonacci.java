package a_Introductory;

public class Fibonacci {
	public int fib(int n) {
		if(n == 0) {
			return 0;
		}
		if(n == 1 || n == 2) {
			return 1;
		}
		return fib(n-2) + fib(n-1);
	}
}
