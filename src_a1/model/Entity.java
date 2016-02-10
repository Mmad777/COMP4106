package model;

abstract public class Entity {
	
	abstract public String getIcon();
	
	protected Position position;
	
	public Entity(Position pos) {
		this.position = pos;
	}
	
	public Entity(int x, int y) {
		this.position = new Position(x, y);
	}
	
	public Position getPosition() {
		return position;
	}
	
}
