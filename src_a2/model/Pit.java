package model;

public class Pit {
	
	private int stones = 0;
	
	public Pit() { 
		// Empty constructor
	}
	
	public int getStones() {
		return stones;
	}
	
	public void addStones(int num) {
		stones += num;
	}
	
	public void removeStones(int num) {
		
		if (stones - num < 0) {
			throw new IllegalArgumentException("Cannot remove more stones than currently present.");
		}
		
		stones -= num;
		
	}
	
	public boolean isEmpty() {
		return stones == 0;
	}

}
