package a2.minimax;

import a2.model.Board;

public class Node {

	private Node parent;
	private int hVal;
	
	private Board state;
	private int selectedPit;
	
	public Node(Node parent, Board state) {
		this.parent = parent;
		this.state = state;
		this.hVal = 0;
		this.selectedPit = 0;
	}
	
	public Node(Node parent, Board state, int selectedPit, int hVal) {
		this(parent, state);
		this.selectedPit = selectedPit;
		this.hVal = hVal;
	}

	public Node getParent() {
		return parent;
	}
	public Board getState() {
		return state;
	}

	public int gethVal() {
		return hVal;
	}
	
	public int getSelectedPit() {
		return selectedPit;
	}

}
