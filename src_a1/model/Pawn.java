package model;

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

	public Pawn(int x, int y) {
		super(x, y);
	}

	@Override
	public String getIcon() {
		return "P";
	}

	/**
	 * @return the moveDirection
	 */
	public MoveDirection getMoveDirection() {
		return moveDirection;
	}

	/**
	 * @param moveDirection the moveDirection to set
	 */
	public void setMoveDirection(MoveDirection moveDirection) {
		this.moveDirection = moveDirection;
	}

	/**
	 * @return the initialX
	 */
	public int getInitialX() {
		return initialX;
	}

	/**
	 * @param initialX the initialX to set
	 */
	public void setInitialX(int initialX) {
		this.initialX = initialX;
	}

	/**
	 * @return the initialY
	 */
	public int getInitialY() {
		return initialY;
	}

	/**
	 * @param initialY the initialY to set
	 */
	public void setInitialY(int initialY) {
		this.initialY = initialY;
	}

}
