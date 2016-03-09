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
	
	private int activePlayer;
	
	public Board(int size, int numStones) {
		this.size = size;
		this.numStones = numStones;
		
		initPits();
		initMancalas();
		
	}
	
	private void initPits() {
		
		pits = Stream.generate(Pit::new).limit(NUM_PLAYERS)
				.map(p -> Stream.generate(Pit::new).limit(size).toArray(Pit[]::new)).toArray(Pit[][]::new);
		
		Stream.of(pits)
	    .flatMap(Stream::of)
	        .forEach(p -> p.addStones(numStones));		
		
	}
	
	private void initMancalas() {
		mancalas = Stream.generate(Mancala::new).limit(NUM_PLAYERS).toArray(Mancala[]::new);	
	}

	public int getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}
	
	public void toggleActivePlayer() {
		activePlayer ^= 1;
	}
	
	public Pit[][] getPits() {
		return pits;
	}
	
	public Mancala[] getMancalas() {
		return mancalas;
	}
	
	/**
	 * Moves a given players stones from a specified pit.
	 * 
	 * @param player - the player selecting the pit
	 * @param pit - the pit number to move
	 * @return true if the player can move again
	 */
	public boolean move(int player, int pit) {
	
		if (player != activePlayer) {
			throw new IllegalArgumentException("Player is not currently active.");
		}
		
		notifyObservers();
		
		return true;
		
	}
	
	public boolean isGameOver() {
		// TODO
		return false;
	}
	
	public void emptyStonesIntoMancalas() {
		// TODO
	}

}
