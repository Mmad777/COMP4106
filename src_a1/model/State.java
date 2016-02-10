package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Pawn.MoveDirection;

public class State {
	
    Logger logger = LoggerFactory.getLogger(State.class);
	
	public final static int BOARD_SIZE = 20;
	private final int NUM_PAWNS = 5;
	
	private Knight knight;
	private List<Pawn> pawns;
	
	public State() {
		this(false);
	}
	
	public State(boolean initialize) {
		
		pawns = new ArrayList<Pawn>();
		
		if (initialize) {
			initBoard();
		}
		
	}
	
	public List<State> generateSuccessors() {
		
		List<State> states = new ArrayList<State>();
		
		// Produce all possible moves for the Knight, and iterate through them (ignoring invalid positions)
		List<Position> kPositions = knight.getNextPositions();
		kPositions.stream().filter(kPos -> isValidPos(kPos)).forEach(kPos -> {
			
			// 1. Create the new state
			State state = new State();
			
			// 2. Add the new knight
			state.setKnight(new Knight(kPos));
			
			// 3. Find all pawns that were not captured
			List<Pawn> nonCapturedPawns1 = filterCapturedPawns(state.getKnight(), pawns);
					
			// 4. For all non-captured pawns, update their positions
			nonCapturedPawns1.stream().forEach(p -> {
				p.updatePosition();
			});
			
			// 5. Again, find all pawns that were not captured
			List<Pawn> nonCapturedPawns2 = filterCapturedPawns(state.getKnight(), nonCapturedPawns1);
			
			// Sort the pawns (x, y)
			Comparator<Pawn> comparator = Comparator.comparing(pawn -> pawn.getPosition().getX());
		    comparator = comparator.thenComparing(Comparator.comparing(pawn -> pawn.getPosition().getY()));
		    List<Pawn> sortedPawns = nonCapturedPawns2.stream().sorted(comparator).collect(Collectors.toList());
			
			// 6. Set the state's pawns
			state.setPawns(sortedPawns);
			
			// Sanity check
			state.getPawns().forEach(p -> {
				
				Position pPos = p.getPosition();
				if (!isValidPos(pPos)) {
					logger.error("ERROR! INVALID PAWN POSITION = " + pPos.getX() + ", " + pPos.getY());
				}
				
			});
			
			// 7. Add the state 
			states.add(state);
			
		});
		
		return states;
		
	}

	private List<Pawn> filterCapturedPawns(Knight knight, List<Pawn> pawns) {
		
		List<Pawn> result = new ArrayList<Pawn>();
		pawns.stream().forEach(p -> {
			if (!knight.getPosition().equals(p.getPosition())) {
				result.add(new Pawn(p));
			}
		});		
		return result;
		
	}
	
	private boolean isValidPos(Position pos) {
		return pos.getX() > 0 && pos.getY() > 0 && pos.getX() < (BOARD_SIZE - 1) && pos.getY() < (BOARD_SIZE - 1);
	}
	
	public boolean allPawnsCaptured() {
		return pawns.isEmpty();
	}
	
	public String getId() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(knight.getPosition());
		pawns.forEach(p -> { sb.append(p.getPosition()); } );
		return sb.toString();
		
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
		knight = new Knight(randomInt(1, BOARD_SIZE - 2), randomInt(1, BOARD_SIZE - 2));		
	}
	
	private void initPawns() {
		
		// TODO - Randomly generate number of pawns?
		IntStream.range(0, NUM_PAWNS).forEach(i -> {
			
			int xMax = BOARD_SIZE - 2;		// -1 for array index and border
			int yMax = BOARD_SIZE - 2;
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
			pawns.add(new Pawn(x, y, x, y, moveDirection));
			
		});
		
	}
	
	private int randomInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max + 1) - min) + min;		
	}

}
