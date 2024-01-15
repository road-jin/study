package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_4 {

	/**
	 인프런 - 자바 알고리즘 문제 풀이
	 4.단어 뒤집기
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Inflearn_1_4 inflearn_1_4 = new Inflearn_1_4();

		int wordRepeatCount = Integer.parseInt(br.readLine());

		for (int i = 0; i < wordRepeatCount; i++) {
			String word = br.readLine();
			System.out.println(inflearn_1_4.flipVer2(word));
		}
	}

	public String flip(String word) {
		StringBuilder stringBuilder = new StringBuilder(word);
		return stringBuilder.reverse().toString();
	}

	public String flipVer2(String word) {
		int lt = 0;
		int rt = word.length() - 1;
		char[] charArray = word.toCharArray();

		while(lt < rt) {
			char temp = charArray[lt];
			charArray[lt++] = charArray[rt];
			charArray[rt--] = temp;
		}

		return String.valueOf(charArray);
	}
}
