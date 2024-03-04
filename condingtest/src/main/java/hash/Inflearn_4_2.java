package hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Inflearn_4_2 {
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		String alphabet1 = in.next();
		String alphabet2 = in.next();
		boolean isAnagram = isAnagram(alphabet1, alphabet2);
		System.out.println(isAnagram ? "YES" : "NO");
	}

	public static boolean isAnagram(String alphabet1, String alphabet2) {
		Map<Character, Integer> alphabetMap1 = convertHashMap(alphabet1);
		Map<Character, Integer> alphabetMap2 = convertHashMap(alphabet2);

		for (Character key : alphabetMap1.keySet()) {
			if (alphabetMap1.get(key) != alphabetMap2.get(key)) {
				return false;
			}
		}

		return true;
	}

	private static Map<Character, Integer> convertHashMap(String alphabet) {
		Map<Character, Integer> result = new HashMap<>();

		for (char key : alphabet.toCharArray()) {
			result.put(key, result.getOrDefault(key, 0) + 1);
		}

		return result;
	}
}
