package array;

import java.util.Scanner;

public class Inflearn_2_12 {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int m = in.nextInt();
		int n = in.nextInt();
		int[][] studentMathRank = getStudentMathRank(m, n, in);
		int count = calculateMentoringCount(studentMathRank);
		System.out.println(count);
	}

	public static int[][] getStudentMathRank(int m, int n, Scanner in) {
		int[][] studentMathRank = new int[n][m];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				studentMathRank[i][j] = in.nextInt();
			}
		}

		return studentMathRank;
	}

	public static int calculateMentoringCount(int[][] studentMathRank) {
		int mentoringCount = 0;

		for (int mento = 1; mento <= studentMathRank[0].length; mento++) {
			for (int menti = 1; menti <= studentMathRank[0].length; menti++) {
				if (mento == menti) {
					continue;
				}

				if (isMentoring(studentMathRank, mento, menti)) {
					mentoringCount++;
				}
			}
		}

		return mentoringCount;
	}

	private static boolean isMentoring(int[][] studentMathRank, int mento, int menti) {
		int count = 0;

		for (int k = 0; k < studentMathRank.length; k++) {
			int mentoRank = 0;
			int mentiRank = 0;

			for (int s = 0; s < studentMathRank[0].length; s++) {
				if (studentMathRank[k][s] == mento) {
					mentoRank = s;
					continue;
				}

				if (studentMathRank[k][s] == menti) {
					mentiRank = s;
				}
			}

			if (mentoRank < mentiRank) {
				count++;
			}
		}

		return count == studentMathRank.length;
	}
}
