package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_10 {

	public static void main(String[] args) throws IOException {
		Inflearn_1_10 inflearn_1_10 = new Inflearn_1_10();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] inputString = br.readLine().split(" ");
		String s = inputString[0];
		char t = inputString[1].charAt(0);

		int[] shortCharaterDistance = inflearn_1_10.getShortCharaterDistance(s, t);
		printArray(shortCharaterDistance);
	}

	public static void printArray(int[] array) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int number : array) {
			stringBuilder.append(number).append(" ");
		}

		System.out.println(stringBuilder);
	}

	public int[] getShortCharaterDistance(String s, char t) {
		int[] result = new int[s.length()];
		changeInOrder(result, s, t);
		changeReverseOrder(result, s, t);
		return result;
	}

	private void changeInOrder(int[] result, String s, char t) {
		int p = 101;

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == t) {
				p = 0;
				result[i] = p;
				continue;
			}

			result[i] = ++p;
		}
	}

	private void changeReverseOrder(int[] result, String s, char t) {
		int p = 101;

		for (int i = s.length() - 1; i >= 0; i--) {
			if (s.charAt(i) == t) {
				p = 0;
				result[i] = Math.min(result[i], p);
				continue;
			}

			result[i] = Math.min(result[i], ++p);
		}
	}

}
