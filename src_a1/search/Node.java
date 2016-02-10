package search;

import model.State;

public class Node {
	
	private Node parent;
	private State state;
	
	public Node(Node parent, State state) {
		this.parent = parent;
		this.state = state;
	}

	public State getState() {
		return state;
	}

}
