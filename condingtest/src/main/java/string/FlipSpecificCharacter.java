package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FlipSpecificCharacter {

	/**
	 인프런 - 자바 알고리즘 문제 풀이
	 5.특정 문자 뒤집기
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		FlipSpecificCharacter flipSpecificCharacter = new FlipSpecificCharacter();

		String randomString = br.readLine();
		System.out.println(flipSpecificCharacter.flip(randomString));
	}

	public String flip(String randomString) {
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
