package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_8 {

	public static void main(String[] args) throws IOException {
		Inflearn_1_8 inflearn_1_8 = new Inflearn_1_8();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputString = br.readLine();

		boolean isPalindrome = inflearn_1_8.isPalindrome(inputString);
		System.out.println(isPalindrome ? "YES" : "NO");
	}

	public boolean isPalindrome(String inputString) {
		inputString = inputString.toUpperCase()
			.replaceAll("[^a-zA-Z]", "");
		String reversedString = new StringBuilder(inputString).reverse()
			.toString();
		return inputString.equalsIgnoreCase(reversedString);
	}
}
