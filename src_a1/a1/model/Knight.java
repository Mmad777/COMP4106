package a1.model;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Entity {

	public Knight(int x, int y) {
		super(x, y);
	}

	public Knight(Position pos) {
		super(pos);
	}

	@Override
	public String getIcon() {
		return "K";
	}

	public List<Position> getNextPositions() {
		
		List<Position> moves = new ArrayList<Position>();
		
		int x = getPosition().getX();
		int y = getPosition().getY();
		
		// top left
		moves.add(new Position(x - 1, y - 2));
		moves.add(new Position(x - 2, y - 1));
		
		// top right
		moves.add(new Position(x + 1, y - 2));
		moves.add(new Position(x + 2, y - 1));
		
		// bottom right
		moves.add(new Position(x + 1, y + 2));
		moves.add(new Position(x + 2, y + 1));
		
		// bottom left
		moves.add(new Position(x - 1, y + 2));
		moves.add(new Position(x - 2, y + 1));
		
		return moves;		
		
	}

}
