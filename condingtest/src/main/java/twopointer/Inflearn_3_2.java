package twopointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Inflearn_3_2 {

	public static void main(String[] args){
		Scanner in= new Scanner(System.in);
		int n = in.nextInt();
		int[] elementsN = getElements(n, in);
		int m = in.nextInt();
		int[] elementsM = getElements(m, in);

		List<Integer> intersection = calculateIntersection(elementsN, elementsM);
		print(intersection);
	}

	public static int[] getElements(int n, Scanner in) {
		int[] elements = new int[n];

		for (int i = 0; i < n; i++) {
			elements[i] = in.nextInt();
		}

		Arrays.sort(elements);
		return elements;
	}

	public static List<Integer> calculateIntersection(int[] elementsN, int[] elementsM) {
		List<Integer> intersection = new ArrayList<>();
		int lt = 0;
		int rt = 0;

		while (lt < elementsN.length && rt < elementsM.length) {
			int elementN = elementsN[lt];
			int elementM = elementsM[rt];

			if (elementN == elementM) {
				lt++;
				rt++;
				intersection.add(elementN);
				continue;
			}

			if (elementN < elementM) {
				lt++;
				continue;
			}

			rt++;
		}

		return intersection;
	}

	public static void print(List<Integer> intersection) {
		for (int element : intersection) {
			System.out.printf("%d ", element);
		}
	}
}
