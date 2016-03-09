package application;

import model.Board;
import model.Pit;

public class MancalaView {
	
	private Board board;
	
	public MancalaView(Board board) {
		
		this.board = board;		
		board.addObserver((obj, arg) -> {
			this.board = (Board) obj;
		});
		
	}
	
	public void draw() {
		
		drawTopPlayer();
		drawBottomPlayer();
		println("");
		
	}
	
	private void drawTopPlayer() {
		
		println("-----                         -----");
		print("| " + board.getMancalas()[0].getStones() + " | ");
		
		for (Pit pit : board.getPits()[0]) {
			print("[" + pit.getStones() + "]");
			print(" ");
		}

		println("|   | ");
		
	}
	
	private void drawBottomPlayer() {
		
		print("|   | ");
		
		for (Pit pit : board.getPits()[1]) {
			print("[" + pit.getStones() + "]");
			print(" ");
		}
		
		println("| " + board.getMancalas()[1].getStones() + " | ");
		println("-----                         -----");
		
	}
	
	private void print(String str) {
		System.out.print(str);
	}
	
	private void println(String str) {
		System.out.println(str);
	}

}
