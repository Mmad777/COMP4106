package model;

import java.util.ArrayList;
import java.util.List;

public class Knight extends BoardEntity {

	public Knight(int x, int y) {
		super(x, y);
	}

	@Override
	public String getIcon() {
		return "K";
	}

	@Override
	public List<Position> getPossibleMoves() {
		
		List<Position> possibleMoves = new ArrayList<Position>();
		
		int x = getPosition().getX();
		int y = getPosition().getY();
		
		// top left
		possibleMoves.add(new Position(x - 1, y - 2));
		possibleMoves.add(new Position(x - 2, y - 1));
		
		// top right
		possibleMoves.add(new Position(x + 1, y - 2));
		possibleMoves.add(new Position(x + 2, y - 1));
		
		// bottom right
		possibleMoves.add(new Position(x + 1, y + 2));
		possibleMoves.add(new Position(x + 2, y + 1));
		
		// bottom left
		possibleMoves.add(new Position(x - 1, y + 2));
		possibleMoves.add(new Position(x - 2, y + 1));
		
		return possibleMoves;		
		
	}

}
