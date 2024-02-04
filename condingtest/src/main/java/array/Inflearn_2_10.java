package array;

import java.util.Scanner;

public class Inflearn_2_10 {

	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int boardSize = in.nextInt();
		int[][] board = createBoard(boardSize, in);
		int peakCount = getPeakCount(board);
		System.out.println(peakCount);
	}

	public static int[][] createBoard(int boardSize, Scanner in) {
		int[][] board = new int[boardSize][boardSize];

		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board[i][j] = in.nextInt();
			}
		}

		return board;
	}

	public static int getPeakCount(int[][] board) {
		int peakCount = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				// 상
				if (i - 1 >= 0 &&
					board[i][j] <= board[i - 1][j]) {
					continue;
				}

				// 우
				if (j + 1 < board.length &&
					board[i][j] <= board[i][j + 1]) {
					continue;
				}

				// 하
				if (i + 1 < board.length &&
					board[i][j] <= board[i + 1][j]) {
					continue;
				}

				// 좌
				if (j - 1 >= 0 &&
					board[i][j] <= board[i][j - 1]) {
					continue;
				}

				peakCount++;
			}
		}

		return peakCount;
	}
}
