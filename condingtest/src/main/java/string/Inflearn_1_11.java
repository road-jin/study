package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_11 {

	public static void main(String[] args) throws IOException {
		Inflearn_1_11 inflearn_1_11 = new Inflearn_1_11();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputText = br.readLine();
		String compressedText = inflearn_1_11.compressText(inputText);
		System.out.println(compressedText);
	}

	public String compressText(String inputText) {
		StringBuilder stringBuilder = new StringBuilder();
		inputText += " ";
		int count = 1;

		for (int i = 0; i < inputText.length() - 1; i++) {
			if (inputText.charAt(i) == inputText.charAt(i + 1)) {
				count++;
				continue;
			}

			stringBuilder.append(inputText.charAt(i));

			if (count > 1) {
				stringBuilder.append(count);
			}

			count = 1;
		}

		return stringBuilder.toString();
	}
}
