package array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inflearn_2_1 {

	public static void main(String[] args) throws IOException {
		Inflearn_2_1 inflearn_2_1 = new Inflearn_2_1();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] numbers = getNumbers(br);

		List<Integer> nextBiggerNumber = inflearn_2_1.getNextBiggerNumber(n, numbers);
		print(nextBiggerNumber);
	}

	private static int[] getNumbers(BufferedReader br) throws IOException {
		return Arrays.stream(br.readLine()
				.split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
	}

	private static void print(List<Integer> nextBiggerNumber) {
		nextBiggerNumber.forEach(n -> System.out.printf("%d ", n));
	}

	private List<Integer> getNextBiggerNumber(int n, int[] numbers) {
		List<Integer> nextBiggerNumbers = new ArrayList<>();
		nextBiggerNumbers.add(numbers[0]);

		for (int i = 1; i < n; i++) {
			if (numbers[i] > numbers[i - 1]) {
				nextBiggerNumbers.add(numbers[i]);
			}
		}

		return nextBiggerNumbers;
	}
}
