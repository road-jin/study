package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_1 {

	/**
	 인프런 - 자바 알고리즘 문제 풀이
	 1.문제 찾기
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String fristString = br.readLine();
		char findCharacter = br.readLine().charAt(0);

		Inflearn_1_1 inflearn_1_1 = new Inflearn_1_1();
		long count = inflearn_1_1.getCount(fristString, findCharacter);

		System.out.println(count);
		br.close();
	}

	public long getCount(String str, char findCharacter) {
		return str.toUpperCase()
			.chars()
			.filter(i -> i == Character.toUpperCase(findCharacter))
			.count();
	}
}
