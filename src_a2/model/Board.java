package model;

public class Board {
	
	private final int DEFAULT_SIZE 			= 6;
	private final int DEFAULT_NUM_STONES 	= 6;
	
	private int size 		= DEFAULT_SIZE;
	private int numStones 	= DEFAULT_NUM_STONES;
	
	public Board(int size, int numStones) {
		this.size = size;
		this.numStones = numStones;
	}

}
