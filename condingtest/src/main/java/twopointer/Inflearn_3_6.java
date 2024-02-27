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
		int tempLength = 0;
		int changeCount = k;

		for (int left = 0; left < binaries.length; left++) {
			for (int right = left; right < binaries.length; right++) {
				if (binaries[right] == 1) {
					tempLength++;
					continue;
				}

				if (changeCount > 0) {
					tempLength++;
					changeCount--;
					continue;
				}

				break;
			}

			changeCount = k;
			maxLength = Math.max(maxLength, tempLength);
			tempLength = 0;
		}

		return maxLength;
	}
}
