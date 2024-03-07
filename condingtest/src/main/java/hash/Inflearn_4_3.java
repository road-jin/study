package hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Inflearn_4_3 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		int[] salesRevenue = getSalesRevenue(n, in);
		List<Integer> salesRevenueTypes = findSalesRevenueTypes(n, k, salesRevenue);
		print(salesRevenueTypes);
	}

	public static int[] getSalesRevenue(int n, Scanner in) {
		int[] result = new int[n];

		for (int i = 0; i < n; i++) {
			result[i] = in.nextInt();
		}

		return result;
	}

	public static List<Integer> findSalesRevenueTypes(int n, int k, int[] salesRevenue) {
		List<Integer> result = new ArrayList<>();
		Map<Integer, Integer> salesRevenueTypes = new HashMap<>();

		for (int i = 0; i < k; i++) {
			salesRevenueTypes.put(salesRevenue[i], salesRevenueTypes.getOrDefault(salesRevenue[i], 0) + 1);
		}

		result.add(salesRevenueTypes.size());

		for (int i = k; i < n; i++) {
			salesRevenueTypes.put(salesRevenue[i], salesRevenueTypes.getOrDefault(salesRevenue[i], 0) + 1);
			salesRevenueTypes.put(salesRevenue[i - k], salesRevenueTypes.get(salesRevenue[i - k]) - 1);

			if (salesRevenueTypes.get(salesRevenue[i - k]) == 0) {
				salesRevenueTypes.remove(salesRevenue[i - k]);
			}

			result.add(salesRevenueTypes.size());
		}

		return result;
	}

	public static void print(List<Integer> salesRevenueTypes) {
		for (int salesRevenueType : salesRevenueTypes) {
			System.out.print(salesRevenueType + " ");
		}
	}
}
