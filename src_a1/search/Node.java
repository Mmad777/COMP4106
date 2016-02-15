package search;

import model.State;

public class Node {

	private static int ID = 0;
	
	private int id;
	private Node parent;
	private State state;
	private int cost;
	
	public Node(Node parent, State state) {
		this.parent = parent;
		this.state = state;
		this.id = Node.ID++;
	}
	
	public Node(Node parent, State state, int cost) {
		this(parent, state);
		this.cost = cost;
	}
	
	public Node getParent() {
		return parent;
	}

	public State getState() {
		return state;
	}
	
	public int getId() {
		return id;
	}
	
	public int getCost() {
		return cost;
	}
	
	@Override
	public String toString() {
		return "Node [" + id + "] parent [" + (parent == null ? "null" : parent.getId()) + "]";
	}

}
