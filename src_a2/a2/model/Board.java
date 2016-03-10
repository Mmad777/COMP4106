package a2.model;

import java.util.Observable;
import java.util.stream.Stream;

import util.MathUtil;

public class Board extends Observable {
	
	private static final int DEFAULT_SIZE 			= 6;
	private static final int DEFAULT_NUM_STONES 	= 6;
	private static final int NUM_PLAYERS			= 2;
	
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
	
	public Board(Board board) {
		this.size = board.size;
		this.numStones = board.numStones;
		
		activePlayer = board.activePlayer;
		
		for (int i=0; i<NUM_PLAYERS; i++) {
			for (int j=0; j<size; j++) {
				this.pits[i][j] = new Pit(board.pits[i][j].getStones());
			}
		}
		
		for (int i=0; i<NUM_PLAYERS; i++) {
			mancalas[i] = new Mancala(board.mancalas[i].getStones());
		}
		
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

	public int getSize() {
		return size;
	}
	
	public static int getNumPlayers() {
		return NUM_PLAYERS;
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
		
		// Allow the player to move again if they selected an empty pit
		if (pit.isEmpty()) {
			return true;
		}

		boolean playAgain = false;

		int currRow = player;
		int currPitNum = pitNum;
		int direction = (currRow == 0 ? -1 : 1);

		int num = pit.getStones();
		while (num != 0) {
			
			currPitNum = currPitNum + (1 * direction);
			
			if (currPitNum < 0 || currPitNum > size - 1) {
			
				int tempRow = currRow;
				currRow ^= 1;
				direction *= -1;
				
				if (tempRow == player) {
					mancalas[player].addStones(1);
					
					// If the last stone is dropped into the mancala, they play again
					if (num == 1) {
						playAgain = true;
						break;
					}
					
				} else {
					continue;
				}
				
			} else {
				
				Pit currPit = pits[currRow][currPitNum];
				
				// If the last stone is dropped into an empty pit on the player's side,
				// all of the stones in the pit directly opposite are placed into
				// their mancala
				if (currRow == player && currPit.isEmpty() && num == 1) {
					Pit oppositePit = pits[currRow ^= 1][currPitNum];
					mancalas[player].addStones(oppositePit.getStones());
					oppositePit.removeAllStones();
				}
				
				currPit.addStones(1);
				
			}
			
			num--;
			
		}
		
		pit.removeAllStones();
		
		setChanged();
		notifyObservers(this);
		
		return playAgain;
		
	}
	
	public boolean isGameOver() {
		return Stream.of(pits).flatMap(Stream::of).filter(p -> !p.isEmpty()).count() == 0;
	}
	
	public void emptyStonesIntoMancalas() {
		// TODO
	}

	public int determineWinner() {
		// TODO Auto-generated method stub
		return 0;
	}

}
