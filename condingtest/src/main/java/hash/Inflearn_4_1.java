package hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inflearn_4_1 {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		String ballotBox = in.next();
		char result = countBallot(ballotBox);
		System.out.println(result);
	}

	public static char countBallot(String ballotBox) {
		Map<Character, Integer> ballotResult = new HashMap<>();

		for (char ballotPaper : ballotBox.toCharArray()) {
			ballotResult.put(ballotPaper, ballotResult.getOrDefault(ballotPaper, 0) + 1);
		}

		int max = Integer.MIN_VALUE;
		char result = ' ';

		for (Character key : ballotResult.keySet()) {
			if (max < ballotResult.get(key)) {
				result = key;
				max = ballotResult.get(key);
			}
		}
		return result;
	}
}
