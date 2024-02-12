package twopointer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inflearn_3_1 {

	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		int n = in.nextInt();
		List<Integer> numbersN = getNumbers(n, in);
		int m = in.nextInt();
		List<Integer> numbersM = getNumbers(m, in);
		List<Integer> mergeNumbers = merge(numbersN, numbersM);
		print(mergeNumbers);
	}

	public static List<Integer> getNumbers(int n, Scanner in) {
		List<Integer> numbers = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			numbers.add(in.nextInt());
		}

		return numbers;
	}

	public static List<Integer> merge(List<Integer> numbersN, List<Integer> numbersM) {
		List<Integer> numbers = new ArrayList<>();
		int lt = 0;
		int rt = 0;
		int numbersNSize = numbersN.size();
		int numbersMSize = numbersM.size();

		while(lt < numbersNSize && rt < numbersMSize) {
			int numberN = numbersN.get(lt);
			int numberM = numbersM.get(rt);

			if (numberN <= numberM) {
				numbers.add(numberN);
				lt++;
				continue;
			}

			numbers.add(numberM);
			rt++;
		}

		while(lt < numbersNSize) {
			numbers.add(numbersN.get(lt++));
		}

		while(rt < numbersMSize) {
			numbers.add(numbersM.get(rt++));
		}

		return numbers;
	}

	public static void print(List<Integer> numbers) {
		for (int n : numbers) {
			System.out.printf("%d ", n);
		}
	}
}
