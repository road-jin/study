package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_6 {

	public static void main(String[] args) throws IOException {
		Inflearn_1_6 inflearn_1_6 = new Inflearn_1_6();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputString = br.readLine();

		String deduplicatedString = inflearn_1_6.removeDuplicateString(inputString);
		System.out.println(deduplicatedString);
	}

	public String removeDuplicateString(String inputString) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < inputString.length(); i++) {
			char charater = inputString.charAt(i);

			if (i == inputString.indexOf(charater)) {
				stringBuilder.append(charater);
			}
		}

		return stringBuilder.toString();
	}
}
