package array;

import java.util.Scanner;

public class Inflearn_2_8 {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int studentCount = in.nextInt();
		int[] koreanScores = getKoreanScores(studentCount, in);
		int[] rankResults = calculateRank(koreanScores);
		print(rankResults);
	}

	private static int[] getKoreanScores(int studentCount, Scanner in) {
		int[] koreanScores = new int[studentCount];

		for (int i = 0; i < studentCount; i++) {
			koreanScores[i] = in.nextInt();
		}

		return koreanScores;
	}

	private static int[] calculateRank(int[] koreanScores) {
		int[] rankResults = new int[koreanScores.length];

		for (int i = 0; i < koreanScores.length; i++) {
			int rank = 1;

			for (int j = 0; j < koreanScores.length; j++) {
				if (i == j) {
					continue;
				}

				if (koreanScores[i] < koreanScores[j]) {
					rank++;
				}
			}

			rankResults[i] = rank;
		}

		return rankResults;
	}

	private static void print(int[] rankResults) {
		for (int rankResult : rankResults) {
			System.out.printf("%d ", rankResult);
		}
	}
}
