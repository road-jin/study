package string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

public class WordsInSentence {

	/**
	 인프런 - 자바 알고리즘 문제 풀이
	 3. 문장속 단어
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String word = br.readLine();

		WordsInSentence wordsInSentence = new WordsInSentence();
		String maxSentence = wordsInSentence.getMaxSentenceVer2(word);

		System.out.println(maxSentence);
		br.close();
	}

	public String getMaxSentence(String word) {
		return Arrays.stream(word.split(" "))
			.max(Comparator.comparingInt(String::length))
			.orElseThrow(IllegalArgumentException::new);
	}

	public String getMaxSentenceVer2(String word) {
		String result = "";
		int maxLength = Integer.MIN_VALUE;

		for (String sentence : word.split(" ")) {
			if (sentence.length() > maxLength) {
				maxLength = sentence.length();
				result = sentence;
			}
		}

		return result;
	}
}
