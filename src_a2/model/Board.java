package model;

import java.util.Observable;
import java.util.stream.Stream;

import util.MathUtil;

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
		
		activePlayer = MathUtil.randomInt(0, 1);
		
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
	 * @param pitNum - the pit number to move
	 * @return true if the player can move again
	 */
	public boolean move(int player, int pitNum) {
	
		if (player != activePlayer) {
			throw new IllegalArgumentException("Player is not currently active.");
		}
		
		Pit pit = pits[player][pitNum];
		
		// Allow the player to select again
		if (pit.isEmpty()) {
			return true;
		}
		
		// Distribute the stones in a counter clockwise direction
		int row = player;
		int initialNum = pit.getStones();
		int numLeft = initialNum;
		while (numLeft != 0) {
			pitNum--;
			
			if (pitNum > 0 && pitNum < size) {
				pits[player][pitNum].addStones(1);
			}
			
			numLeft--;
		}
		
		// Remove them all from the initial pit
		pit.removeStones(initialNum);
		
		setChanged();
		notifyObservers(this);		
		return true;
		
	}
	
	public boolean isGameOver() {
		// TODO
		return false;
	}
	
	public void emptyStonesIntoMancalas() {
		// TODO
	}

	public int determineWinner() {
		// TODO Auto-generated method stub
		return 0;
	}

}
