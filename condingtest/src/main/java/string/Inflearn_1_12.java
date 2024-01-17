package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_12 {

	public static void main(String[] args) throws IOException {
		Inflearn_1_12 inflearn_1_12 = new Inflearn_1_12();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int inputCharaterCount = Integer.parseInt(br.readLine());
		String inputEncryption = br.readLine();

		String decryptedText = inflearn_1_12.decrypt(inputCharaterCount, inputEncryption);
		System.out.println(decryptedText);

	}

	private String decrypt(int inputCharaterCount, String inputEncryption) {
		StringBuilder stringBuilder = new StringBuilder();
		inputEncryption = inputEncryption.replace("#", "1")
			.replace("*", "0");

		for(int i = 0; i < inputCharaterCount; i++) {
			int beginIndex = i * 7;
			int endIndex = beginIndex + 7;
			String charater = inputEncryption.substring(beginIndex, endIndex);
			int decimal = Integer.parseInt(charater, 2);
			stringBuilder.append((char) decimal);
		}

		return stringBuilder.toString();
	}
}
