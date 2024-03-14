package hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inflearn_4_4 {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		String s = in.next();
		String t = in.next();
		int count = getAnagramCount(s, t);
		System.out.println(count);
	}

	public static int getAnagramCount(String s, String t) {
		Map<Character, Integer> actualAlphabet = convertHashMap(t);
		Map<Character, Integer> expectedAlphabet = new HashMap<>();
		int count = initalizedExpectedAlphabet(s, t, actualAlphabet, expectedAlphabet);

		for (int i = t.length(); i < s.length(); i++) {
			expectedAlphabet.put(s.charAt(i), expectedAlphabet.getOrDefault(s.charAt(i), 0) + 1);
			char removeKey = s.charAt(i - t.length());
			expectedAlphabet.put(removeKey, expectedAlphabet.getOrDefault(removeKey, 0) - 1);

			if (expectedAlphabet.get(removeKey) == 0) {
				expectedAlphabet.remove(removeKey);
			}

			if (isAnagram(actualAlphabet, expectedAlphabet)) {
				count++;
			}
		}

		return count;
	}

	private static Map<Character, Integer> convertHashMap(String alphabet) {
		Map<Character, Integer> result = new HashMap<>();

		for (char key : alphabet.toCharArray()) {
			result.put(key, result.getOrDefault(key, 0) + 1);
		}

		return result;
	}

	private static int initalizedExpectedAlphabet(String s, String t, Map<Character, Integer> actualAlphabet,
		Map<Character, Integer> expectedAlphabet) {
		int count = 0;

		for(int i = 0; i < t.length(); i++) {
			expectedAlphabet.put(s.charAt(i), expectedAlphabet.getOrDefault(s.charAt(i), 0) + 1);
		}

		if (isAnagram(actualAlphabet, expectedAlphabet)) {
			count++;
		}

		return count;
	}

	public static boolean isAnagram(Map<Character, Integer> actual, Map<Character, Integer> expected) {
		if (actual.size() != expected.size()) {
			return false;
		}

		for (Character key : actual.keySet()) {
			if (actual.get(key) != expected.get(key)) {
				return false;
			}
		}

		return true;
	}
}
