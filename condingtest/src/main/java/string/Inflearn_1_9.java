package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_9 {

	public static void main(String[] args) throws IOException {
		Inflearn_1_9 inflearn_1_9 = new Inflearn_1_9();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputString = br.readLine();

		int extractedNumber = inflearn_1_9.extractNumber(inputString);
		System.out.println(extractedNumber);
	}

	public int extractNumber(String inputString) {
		int result = 0;

		for(char charater : inputString.toCharArray()) {
			if (Character.isDigit(charater)) {
				result = result * 10 + (charater - 48);
			}
		}

		return result;
	}
}
