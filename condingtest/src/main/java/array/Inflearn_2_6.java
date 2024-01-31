package array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Inflearn_2_6 {

	public static void main(String[] args) throws IOException {
		Inflearn_2_6 inflearn_2_6 = new Inflearn_2_6();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] reversNumbers = inflearn_2_6.reversNumbers(n, br.readLine());
		List<Integer> decimals = inflearn_2_6.getDecimals(reversNumbers);
		print(decimals);
	}

	public List<Integer> getDecimals(int[] reversNumbers) {
		List<Integer> decimals = new ArrayList<>();

		for (int reversNumber : reversNumbers) {
			if (isDecimal(reversNumber)) {
				decimals.add(reversNumber);
			}
		}

		return decimals;
	}

	private boolean isDecimal(int number) {
		if (number <= 1) {
			return false;
		}

		for (int i = 2; i < number; i++) {
			if (number % i == 0) {
				return false;
			}
		}

		return true;
	}

	private static void print(List<Integer> decimals) {
		decimals.forEach(n -> System.out.printf("%d ", n));
	}

	public int[] reversNumbers(int n, String numbers) {
		int[] reversNumbers = new int[n];
		String[] split = numbers.split(" ");

		for (int i = 0; i < n; i++) {
			int number = Integer.parseInt(split[i]);
			int temp = number;
			int reversNumber = 0;

			while(temp > 0 ) {
				reversNumber = reversNumber * 10 + temp % 10;
				temp /= 10;
			}

			reversNumbers[i] = reversNumber;
		}

		return reversNumbers;
	}

}
