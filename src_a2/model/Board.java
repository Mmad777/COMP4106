package model;

import java.util.Observable;
import java.util.stream.Stream;

public class Board extends Observable {
	
	private final int DEFAULT_SIZE 			= 6;
	private final int DEFAULT_NUM_STONES 	= 6;
	private final int NUM_PLAYERS			= 2;
	
	private int size 		= DEFAULT_SIZE;
	private int numStones 	= DEFAULT_NUM_STONES;
	
	private Pit[][] pits;
	private Mancala[] mancalas;
	
	public Board(int size, int numStones) {
		this.size = size;
		this.numStones = numStones;
		
		initPits();
		initMancalas();
		
	}
	
	private void initPits() {
		pits = Stream.generate(Pit::new).limit(NUM_PLAYERS)
				.map(p -> Stream.generate(Pit::new).limit(size).toArray(Pit[]::new)).toArray(Pit[][]::new);
		
	}
	
	private void initMancalas() {
		mancalas = Stream.generate(Mancala::new).limit(NUM_PLAYERS).toArray(Mancala[]::new);		
	}
	
	public Pit[][] getPits() {
		return pits;
	}
	
	public Mancala[] getMancalas() {
		return mancalas;
	}
	
	public boolean isGameOver() {
		// TODO
		return false;
	}
	
	public void move() {
		notifyObservers();
	}

}
