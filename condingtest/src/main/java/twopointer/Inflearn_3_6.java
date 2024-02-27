package twopointer;

import java.util.Scanner;

public class Inflearn_3_6 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		int[] binaries = getBinaries(n, in);
		int maxLength = getMaxLength(binaries, k);
		System.out.println(maxLength);
	}

	public static int[] getBinaries(int n, Scanner in) {
		int[] binaries = new int[n];

		for (int i = 0; i < n; i++) {
			binaries[i] = in.nextInt();
		}

		return binaries;
	}

	public static int getMaxLength(int[] binaries, int k) {
		int maxLength = 0;
		int count = 0;
		int left = 0;

		for (int right = 0; right < binaries.length; right++) {
			if (binaries[right] == 0) {
				count++;
			}

			while (count > k) {
				if (binaries[left] == 0) {
					count--;
				}

				left++;
			}

			maxLength = Math.max(maxLength, right - left + 1);
		}

		return maxLength;
	}
}
