package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import model.Pawn.MoveDirection;

public class Board {
	
	public final static int SIZE = 15;
	private final int NUM_PAWNS = 5;
	
	private List<BoardEntity> boardEntities;
	
	public Board() {
		generateBoard();
	}
	
	public List<BoardEntity> getBoardEntities() {
		return boardEntities;
	}
	
	private void generateBoard() {
		
		// Initialize the list of entities 
		boardEntities = new ArrayList<BoardEntity>();
		
		Random rand = new Random();
		
		// Place the knight anywhere
		boardEntities.add(new Knight(rand.nextInt(SIZE - 1), rand.nextInt(SIZE - 1)));
		
		// TODO - Randomly generate number of pawns?
		IntStream.range(0, NUM_PAWNS).forEach(i -> {
			
			// Subtract 2 from upper bounds to account for array index and border, and
			// 	add 1 to lower bounds to account for array index
			int rowUpperBound = SIZE - 2;
			int colUpperBound = SIZE - 2;
			int rowLowerBound = 1;
			int colLowerBound = 1;			
			
			// Generate a random MoveDirection
			MoveDirection moveDirection = MoveDirection.getRandom();
			
			// Set bounds so the pawns do not enter the border
			switch (moveDirection) {
				case UP:
					rowLowerBound++;
					break;
				case RIGHT:
					colUpperBound--;
					break;
				case LEFT:
					colLowerBound++;
					break;
				case DOWN:
					rowUpperBound--;
					break;
			}
			
			int x = rand.nextInt(rowUpperBound) + rowLowerBound;
			int y = rand.nextInt(colUpperBound) + colLowerBound;
			System.out.println("Generating pawn at :  x = " + x + ", y = " + y + " with direction = " + moveDirection);
			
			// Place the pawn
			boardEntities.add(new Pawn(x, y));
			
		});
		
	}

}
