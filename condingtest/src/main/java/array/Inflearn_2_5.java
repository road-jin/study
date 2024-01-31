package array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_2_5 {

	public static void main(String[] args) throws IOException {
		Inflearn_2_5 inflearn_2_5 = new Inflearn_2_5();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int decimalCount = inflearn_2_5.getDecimalCount(n);
		System.out.println(decimalCount);
	}

	public int getDecimalCount(int n) {
		boolean[] decimals = new boolean[n + 1];
		int count = 0;

		for (int i = 2; i <= n; i++) {
			if (!decimals[i]) {
				count++;
			}

			for (int j = i + i; j <= n; j += i) {
				decimals[j] = true;
			}
		}

		return count;
	}
}
