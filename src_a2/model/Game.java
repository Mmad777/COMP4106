package model;

import java.util.Observable;

import util.MathUtil;

public class Game extends Observable {
	
	private Board board;
	
	public Game(int size, int numStones) {
		
		board = new Board(size, numStones);
		board.setActivePlayer(MathUtil.randomInt(0, 1));

		board.addObserver( (obj, arg) -> {
            notifyObservers(board);
        });
		
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int determineWinner() {
		// TODO
		return -1;
	}

}
