package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_2 {

	/**
	 인프런 - 자바 알고리즘 문제 풀이
	 2. 대소문자 변환
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputString = br.readLine();

		Inflearn_1_2 inflearn_1_2 = new Inflearn_1_2();
		String chagedString = inflearn_1_2.change(inputString);

		System.out.println(chagedString);
		br.close();
	}

	public String change(String inputString) {
		StringBuilder stringBuilder = new StringBuilder();

		for (char c : inputString.toCharArray()) {
			if (Character.isUpperCase(c)) {
				stringBuilder.append(Character.toLowerCase(c));
				continue;
			}

			if (Character.isLowerCase(c)) {
				stringBuilder.append(Character.toUpperCase(c));
			}
		}

		return stringBuilder.toString();
	}
}
