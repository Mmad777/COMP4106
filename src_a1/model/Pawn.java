package model;

import java.util.List;

public class Pawn extends BoardEntity {

	public enum MoveDirection {
		LEFT,
		RIGHT,
		UP, 
		DOWN;
		
		public static MoveDirection getRandom() {
	        return values()[(int) (Math.random() * values().length)];
	    }
	}
	
	private MoveDirection moveDirection;
	private int initialX;
	private int initialY;

	public Pawn(int x, int y, MoveDirection moveDirection) {
		super(x, y);
		this.moveDirection = moveDirection;
	}

	@Override
	public String getIcon() {
		return "P" + "[" + moveDirection.toString().charAt(0) + "]";
	}

	public MoveDirection getMoveDirection() {
		return moveDirection;
	}

	public void setMoveDirection(MoveDirection moveDirection) {
		this.moveDirection = moveDirection;
	}

	public int getInitialX() {
		return initialX;
	}

	public void setInitialX(int initialX) {
		this.initialX = initialX;
	}

	public int getInitialY() {
		return initialY;
	}

	public void setInitialY(int initialY) {
		this.initialY = initialY;
	}

	@Override
	public List<Position> getPossibleMoves() {
		// TODO Auto-generated method stub
		return null;
	}

}
