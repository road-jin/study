package array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Inflearn_2_2 {

	public static void main(String[] args) throws IOException {
		Inflearn_2_2 inflearn_2_2 = new Inflearn_2_2();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] studentHeights = getStudentHeights(br);

		int visibleStudentCount = inflearn_2_2.getVisibleStudentCount(n, studentHeights);
		System.out.println(visibleStudentCount);
	}

	private static int[] getStudentHeights(BufferedReader br) throws IOException {
		return Arrays.stream(br.readLine()
				.split(" "))
			.mapToInt(Integer::parseInt)
			.toArray();
	}

	public int getVisibleStudentCount(int n, int[] studentHeights) {
		int previousStudentHeight = studentHeights[0];
		int count = 1;

		for (int i = 1; i < n; i++) {
			if (previousStudentHeight < studentHeights[i]) {
				previousStudentHeight = studentHeights[i];
				count++;
			}
		}

		return count;
	}
}
