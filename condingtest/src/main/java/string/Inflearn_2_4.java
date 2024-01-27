package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_2_4 {

	public static void main(String[] args) throws IOException {
		Inflearn_2_4 inflearn_2_4 = new Inflearn_2_4();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		inflearn_2_4.printFibonacciNumbers(n);
	}

	public void printFibonacciNumbers(int n) {
		int a = 1;
		int b = 1;
		int c = Integer.MIN_VALUE;
		System.out.printf("%d %d ", a, b);

		for (int i = 2; i < n; i++) {
			c = a + b;
			a = b;
			b = c;
			System.out.printf("%d ", c);
		}
	}
}
