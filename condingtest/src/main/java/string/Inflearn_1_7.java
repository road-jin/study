package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inflearn_1_7 {

	public static void main(String[] args) throws IOException {
		Inflearn_1_7 inflearn_1_7 = new Inflearn_1_7();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputString = br.readLine();

		boolean isPalindrome = inflearn_1_7.isPalindrome(inputString);
		System.out.println(isPalindrome ? "YES" : "NO");
	}

	public boolean isPalindrome(String inputString) {
		inputString = inputString.toUpperCase();
		int lt = 0;
		int rt = inputString.length() - 1;

		while(lt < rt) {
			if (inputString.charAt(lt++) != inputString.charAt(rt--)) {
				return false;
			}
		}

		return true;
	}
}
