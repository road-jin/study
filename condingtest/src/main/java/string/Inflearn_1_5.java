package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_5 {

	/**
	 인프런 - 자바 알고리즘 문제 풀이
	 5.특정 문자 뒤집기
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Inflearn_1_5 inflearn_1_5 = new Inflearn_1_5();

		String randomString = br.readLine();
		System.out.println(inflearn_1_5.flipSpecificCharacter(randomString));
	}

	public String flipSpecificCharacter(String randomString) {
		int lt = 0;
		int rt = randomString.length() - 1;
		char[] charArray = randomString.toCharArray();

		while(lt < rt) {
			if (!Character.isAlphabetic(charArray[lt])) {
				lt++;
				continue;
			}

			if (!Character.isAlphabetic(charArray[rt])) {
				rt--;
				continue;
			}


			char temp = charArray[lt];
			charArray[lt++] = charArray[rt];
			charArray[rt--] = temp;
		}

		return String.valueOf(charArray);
	}
}
