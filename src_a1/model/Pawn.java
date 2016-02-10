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
		
		Position oldPos = new Position(this.position);
		//System.out.println("Current pos = " + this.position);
		switch (moveDirection) {
			case LEFT:
				this.position.setX(initialPosition() ? initialX - 1 : initialX);
				break;
			case RIGHT:
				this.position.setX(initialPosition() ? initialX + 1 : initialX);
				break;
			case UP:
				this.position.setY(initialPosition() ? initialY - 1 : initialY);
				break;
			case DOWN:
				this.position.setY(initialPosition() ? initialY + 1 : initialY);
				break;
		}		
		
		if (oldPos.equals(this.position)) {
			System.out.println("ERROR! POSITION DIDNT UPDATE!");
		}

		//System.out.println("New pos = " + this.position);
		
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
