package twopointer;

import java.util.Scanner;

public class Inflearn_3_4 {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int m = in.nextInt();
		int[] numbers = getNumbers(n, in);

		int count = getCountOfContinuousPartialSequences(numbers, m);
		System.out.println(count);
	}

	public static int[] getNumbers(int n, Scanner in) {
		int[] numbers = new int[n];

		for (int i = 0; i < n; i++) {
			numbers[i] = in.nextInt();
		}

		return numbers;
	}

	public static int getCountOfContinuousPartialSequences(int[] numbers, int m) {
		int count = 0;
		int sum = 0;
		int firstIndex = 0;

		for (int i = 0; i < numbers.length; i++) {
			sum += numbers[i];

			if (sum == m) {
				count++;
			}

			while (sum >= m) {
				sum -= numbers[firstIndex++];

				if (sum == m) {
					count++;
				}
			}
		}

		return count;
	}
}
