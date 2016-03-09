package model;

import java.util.Observable;

import util.MathUtil;

public class Game extends Observable {
	
	private Board board;
	private int activePlayer;
	
	public Game(int size, int numStones) {
		
		board = new Board(size, numStones);
		activePlayer = MathUtil.randomInt(0, 1);

		board.addObserver( (obj, arg) -> {
            notifyObservers(board);
        });
		
	}
	
	public Board getBoard() {
		return board;
	}

}
