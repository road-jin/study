package twopointer;

import java.util.Scanner;

public class Inflearn_3_5 {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int count = getCountOfContinuousNumberSum(n);
		System.out.println(count);
	}

	public static int getCountOfContinuousNumberSum(int n) {
		int left = 1;
		int right = 1;
		int count = 0;
		int sum = left;

		while (left < n) {
			if (++right == n) {
				sum = ++left;
				right = left;
				continue;
			}

			sum += right;

			if (sum == n) {
				count++;
				sum = ++left;
				right = left;
			}
		}

		return count;
	}
}
