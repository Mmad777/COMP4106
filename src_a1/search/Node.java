package search;

import model.BoardState;

public class Node {
	
	private Node parent;
	private BoardState state;
	
	public Node(Node parent, BoardState state) {
		this.parent = parent;
		this.state = state;
	}

	public BoardState getState() {
		return state;
	}

}
