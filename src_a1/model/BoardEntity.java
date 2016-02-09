package model;

import java.util.List;

abstract public class BoardEntity {
	
	abstract public String getIcon();
	abstract public List<Position> getPossibleMoves();
	
	private Position position;
	
	public BoardEntity(int x, int y) {
		this.position = new Position(x, y);
	}
	
	public Position getPosition() {
		return position;
	}
	
	public class Position {
		int x;
		int y;
		
		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public int getY() {
			return y;
		}
		
		public void setY(int y) {
			this.y = y;
		}
	}

}
