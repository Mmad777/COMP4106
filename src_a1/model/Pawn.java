package model;

public class Pawn extends Entity {

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
	
	public Pawn(int initialX, int initialY, int x, int y, MoveDirection moveDirection) {
		super(x, y);
		this.initialX  = initialX;
		this.initialY = initialY;
		this.moveDirection = moveDirection;
		//System.out.println("Initializing pawn [" + x + ", " + y + "] - initial [" + initialX + ", " + initialY + "]");
	}

	public Pawn(Pawn p) {
		this(p.getInitialX(), p.getInitialY(), p.getPosition().getX(), p.getPosition().getY(), p.getMoveDirection());
	}

	@Override
	public String getIcon() {
		return "P";
	}
	
	public void updatePosition() {
		this.position = getNextPosition();
	}

	public Position getNextPosition() {

		int newX = position.x;
		int newY = position.y;
		switch (moveDirection) {
			case LEFT:
				newX = initialPosition() ? initialX - 1 : initialX;
				break;
			case RIGHT:
				newX = initialPosition() ? initialX + 1 : initialX;
				break;
			case UP:
				newY = initialPosition() ? initialY - 1 : initialY;
				break;
			case DOWN:
				newY = initialPosition() ? initialY + 1 : initialY;
				break;
		}
		
		if (newX == position.x || newY == position.y) {
			System.out.println("ERROR");
		}
		
		return new Position(newX, newY);
		
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
	
	private boolean initialPosition() {
		return initialX == position.x && initialY == position.y;
	}

}
