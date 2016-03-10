package a2.model;

public class Mancala {
	
	private int stones = 0;
	
	public Mancala() {
		// Empty constructor
	}
	
	public Mancala(int stones) {
		this.stones = stones;
	}
	
	public int getStones() {
		return stones;
	}
	
	public void addStones(int num) {
		stones += num;
	}

}
