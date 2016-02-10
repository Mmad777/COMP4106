package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Pawn.MoveDirection;

public class State {
	
    Logger logger = LoggerFactory.getLogger(State.class);
	
	public final static int SIZE = 15;
	private final int NUM_PAWNS = 5;
	
	private Knight knight;
	private List<Pawn> pawns;
	
	public State(boolean initialize) {
		
		pawns = new ArrayList<Pawn>();
		
		if (initialize) {
			initBoard();
		}
		
	}
	
	public boolean isFinished() {
		return pawns.isEmpty();
	}
	
	public Knight getKnight() {
		return knight;
	}

	public void setKnight(Knight knight) {
		this.knight = knight;
	}

	public List<Pawn> getPawns() {
		return pawns;
	}

	public void setPawns(List<Pawn> pawns) {
		this.pawns = pawns;
	}

	private void initBoard() {
		
		initKnight();
		initPawns();
		
	}
	
	private void initKnight() {

		knight = new Knight(randomInt(1, SIZE - 2), randomInt(1, SIZE - 2));
		
	}
	
	private void initPawns() {
		
		// TODO - Randomly generate number of pawns?
		IntStream.range(0, NUM_PAWNS).forEach(i -> {
			
			int xMax = SIZE - 2;		// -1 for array index and border
			int yMax = SIZE - 2;
			int xMin = 1;				// 1 for array index
			int yMin = 1;			
			
			// Generate a random MoveDirection
			MoveDirection moveDirection = MoveDirection.getRandom();
			
			// Set bounds so the pawns do not enter the border
			switch (moveDirection) {
				case UP:
					yMin++;
					break;
				case RIGHT:
					xMax--;
					break;
				case LEFT:
					xMin++;
					break;
				case DOWN:
					yMax--;
					break;
			}

			// Generate semi-random co-ordinates and add to the list of entities
			int x = randomInt(xMin, xMax);
			int y = randomInt(yMin, yMax);
			pawns.add(new Pawn(x, y, moveDirection));
			
		});
		
	}
	
	private int randomInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max + 1) - min) + min;		
	}

}
