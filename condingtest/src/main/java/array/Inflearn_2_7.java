package array;

import java.util.Scanner;

public class Inflearn_2_7 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int questionCount = in.nextInt();
		int[] questionResults = getQuestionResults(questionCount, in);
		System.out.println(soluction(questionResults));
	}

	public static int[] getQuestionResults(int questionCount, Scanner in) {
		int[] questionResults = new int[questionCount];

		for (int i = 0; i < questionCount; i++) {
			questionResults[i] = in.nextInt();
		}

		return questionResults;
	}

	public static int soluction(int[] questionResults) {
		int result = 0;
		int score = 1;

		for (int questionResult : questionResults) {
			if (questionResult == 1) {
				result += score++;
				continue;
			}

			score = 1;
		}

		return result;
	}
}
