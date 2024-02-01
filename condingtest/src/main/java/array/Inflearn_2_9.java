package array;

import java.util.Scanner;

public class Inflearn_2_9 {

	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		int boardSize = in.nextInt();
		int[][] board = getBoard(boardSize, in);
		System.out.println(getMaxSum(board));
	}

	private static int[][] getBoard(int boardSize, Scanner in) {
		int[][] board = new int[boardSize][boardSize];

		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				board[i][j] = in.nextInt();
			}
		}

		return board;
	}

	private static int getMaxSum(int[][] board) {
		int maxSum = getMaxSumDiagonal(board);

		for (int i = 0; i < board.length; i++) {
			maxSum = Math.max(maxSum, getSumHorizontal(board, i));
			maxSum = Math.max(maxSum, getSumVertical(board, i));
		}

		return maxSum;
	}

	private static int getSumHorizontal(int[][] board, int index) {
		int sum = 0;

		for (int j = 0; j < board.length; j++) {
			sum += board[index][j];
		}

		return sum;
	}

	private static int getSumVertical(int[][] board, int index) {
		int sum = 0;

		for (int j = 0; j < board.length; j++) {
			sum += board[j][index];
		}

		return sum;
	}

	private static int getMaxSumDiagonal(int[][] board) {
		int sumLeftDiagonal = 0;
		int sumRightDiagonal = 0;

		for (int i = 0; i < board.length; i++) {
			sumLeftDiagonal += board[i][i];
			sumRightDiagonal += board[i][board.length - 1 - i];
		}

		return Math.max(sumLeftDiagonal, sumRightDiagonal);
	}
}
