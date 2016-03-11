package a2.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import a2.model.Board;
import a2.model.Pit;

public class MancalaView {
	
	private Board board;
	
	public MancalaView(Board board) {
		
		this.board = board;		
		board.addObserver((obj, arg) -> {
			this.board = (Board) obj;
			displayBoard();
		});
		
	}
	
	public void displayBoard() {

		println("");
		drawTopPlayer();
		drawBottomPlayer();
		println("");
		
	}

	public void displayWinner(int playerNum) {
		println("Player " + playerNum + " is the winner!");
	}

	public void displayGameOver() {
		println("Game over! Emptying stones into mancalas...");
	}
	
	public int getPitSelection() throws NumberFormatException, IOException {
		
		BufferedReader br  = new  BufferedReader(new InputStreamReader(System.in));
		print("Player " + board.getActivePlayer() + ", please enter a pit number (0-5): ");
		int num = Integer.parseInt(br.readLine());
		return num;
		
	}
	
	private void drawTopPlayer() {
		
		println("-----                                ------");
		print(String.format("| %1$2s | ", String.valueOf(board.getMancalas()[0].getStones())));
		
		for (Pit pit : board.getPits()[0]) {
			print(String.format("[%1$2s]", String.valueOf(pit.getStones())));
			print(" ");
		}

		println("|    | ");
		
	}
	
	private void drawBottomPlayer() {
		
		print("|    | ");
		
		for (Pit pit : board.getPits()[1]) {
			print(String.format("[%1$2s]", String.valueOf(pit.getStones())));
			print(" ");
		}

		println(String.format("| %1$2s | ", String.valueOf(board.getMancalas()[1].getStones())));
		println("-----                                ------");
		
	}
	
	private void print(String str) {
		System.out.print(str);
	}
	
	private void println(String str) {
		System.out.println(str);
	}

}
