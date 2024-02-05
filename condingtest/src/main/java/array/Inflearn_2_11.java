package array;

import java.util.Scanner;

public class Inflearn_2_11 {

	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		int studentCount = in.nextInt();
		int[][] classByGrade = getClassByGrades(studentCount, in);
		int tempReader = pickTempReader(classByGrade);
		System.out.println(tempReader);
	}

	public static int[][] getClassByGrades(int studentCount, Scanner in) {
		int classesCount = 5;
		int[][] classByGrade = new int[studentCount][classesCount];

		for (int i = 0; i < studentCount; i++) {
			for (int j = 0; j < classesCount; j++) {
				classByGrade[i][j] = in.nextInt();
			}
		}

		return classByGrade;
	}

	public static int pickTempReader(int[][] classByGrade) {
		int tempReader = 1;
		int maxSameClassFriendCount = 0;

		for (int i = 0; i < classByGrade.length; i++) {
			int sameClassFriendCount = 0;

			for (int j = 0; j < classByGrade.length; j++) {
				if (i == j) {
					continue;
				}

				for (int k = 0; k < 5; k++) {
					if (classByGrade[i][k] == classByGrade[j][k]) {
						sameClassFriendCount++;
						break;
					}
				}
			}

			if (sameClassFriendCount > maxSameClassFriendCount) {
				maxSameClassFriendCount = sameClassFriendCount;
				tempReader = i + 1;
			}
		}

		return tempReader;
	}
}
