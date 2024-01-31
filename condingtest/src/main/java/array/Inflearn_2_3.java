package array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_2_3 {

	public static void main(String[] args) throws IOException {
		Inflearn_2_3 inflearn_2_3 = new Inflearn_2_3();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] rockPaperScissorsA = parseRockPaperScissors(n, br.readLine().split(" "));
		int[] rockPaperScissorsB = parseRockPaperScissors(n, br.readLine().split(" "));

		inflearn_2_3.playRockPaperScissors(n, rockPaperScissorsA, rockPaperScissorsB);
	}

	private static int[] parseRockPaperScissors(int n, String[] rockPaperScissorsSplit) {
		int[] rockPaperScissorsNumbers = new int[n];

		for (int i = 0; i < n; i++) {
			rockPaperScissorsNumbers[i] = Integer.parseInt(rockPaperScissorsSplit[i]);
		}

		return rockPaperScissorsNumbers;
	}

	public void playRockPaperScissors(int n, int[] playerA, int[] playerB) {
		for (int i = 0; i < n; i++) {
			if (playerA[i] == playerB[i]) {
				System.out.println("D");
				continue;
			}

			if (playerA[i] == 1 && playerB[i] == 3) {
				System.out.println("A");
				continue;
			}

			if (playerA[i] == 2 && playerB[i] == 1) {
				System.out.println("A");
				continue;
			}

			if (playerA[i] == 3 && playerB[i] == 2) {
				System.out.println("A");
				continue;
			}

			System.out.println("B");
		}
	}
}
