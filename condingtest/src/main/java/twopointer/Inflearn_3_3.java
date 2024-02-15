package twopointer;

import java.util.Scanner;

public class Inflearn_3_3 {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		int[] revenueHistory = getRevenueHistory(n, in);

		int maxRevenue = calculateMaxRevenue(k, revenueHistory);
		System.out.println(maxRevenue);
	}

	public static int[] getRevenueHistory(int n, Scanner in) {
		int[] revenueHistory = new int[n];

		for (int i = 0; i < n; i++) {
			revenueHistory[i] = in.nextInt();
		}

		return revenueHistory;
	}

	public static int calculateMaxRevenue(int k, int[] revenueHistory) {
		int maxRevenue = getFirstMaxRevenue(k, revenueHistory);
		int previousSumRevenue = maxRevenue;

		for (int i = k; i < revenueHistory.length; i++) {
			int sumRevenue = previousSumRevenue - revenueHistory[i - k] + revenueHistory[i];
			previousSumRevenue = sumRevenue;

			if (sumRevenue > maxRevenue) {
				maxRevenue = sumRevenue;
			}
		}

		return maxRevenue;
	}

	public static int getFirstMaxRevenue(int k, int[] revenueHistory) {
		int maxRevenue = 0;

		for (int i = 0; i < k; i++) {
			maxRevenue += revenueHistory[i];
		}
		return maxRevenue;
	}
}
