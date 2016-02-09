package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import model.Pawn.MoveDirection;

public class BoardState {
	
	public final static int SIZE = 15;
	private final int NUM_PAWNS = 5;
	
	private List<BoardEntity> boardEntities;
	
	public BoardState() {
		generateBoard();
	}
	
	public BoardEntity getBoardEntity(int x, int y) {
		return boardEntities.stream().filter(e -> e.getPosition().getX() == x && e.getPosition().getY() == y).findFirst().get();
	}
	
	public List<BoardEntity> getBoardEntities() {
		return boardEntities;
	}
	
	private void generateBoard() {
		
		// Initialize the list of entities 
		boardEntities = new ArrayList<BoardEntity>();
		
		
		// Place the knight anywhere
		boardEntities.add(new Knight(randomInt(1, SIZE - 1), randomInt(1, SIZE - 1)));
		
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
			boardEntities.add(new Pawn(x, y, moveDirection));
			
		});
		
	}
	
	private int randomInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max + 1) - min) + min;		
	}

}
