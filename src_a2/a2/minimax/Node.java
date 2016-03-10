package a2.minimax;

import a2.model.Board;

public class Node {

	private Node parent;
	private Board state;
	private int hVal;
	
	public Node(Node parent, Board state) {
		this.parent = parent;
		this.state = state;
		this.hVal = -1;
	}
	
	public Node(Node parent, Board state, int hVal) {
		this(parent, state);
		this.hVal = hVal;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Board getState() {
		return state;
	}

	public void setState(Board state) {
		this.state = state;
	}

	public int gethVal() {
		return hVal;
	}

	public void sethVal(int hVal) {
		this.hVal = hVal;
	}

}
