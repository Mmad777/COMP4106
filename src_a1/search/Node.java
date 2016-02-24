package search;

import model.State;

public class Node {
	private static int ID = 0;
	
	private int id;
	private Node parent;
	private State state;
	
	private int hCost; 
	private int gCost;
	
	public Node(Node parent, State state) {
		this.parent = parent;
		this.state = state;
		this.id = Node.ID++;
		this.hCost = 0;
		this.gCost = 0;
	}
	
	public Node(Node parent, State state, int gCost, int hCost) {
		this(parent, state);
		this.gCost = gCost;
		this.hCost = hCost;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}

	public State getState() {
		return state;
	}
	
	public int getId() {
		return id;
	}
	
	public int gethCost() {
		return hCost;
	}

	public void sethCost(int hCost) {
		this.hCost = hCost;
	}

	public int getGCost() {
		return gCost;
	}

	public void setGCost(int cost) {
		this.gCost = cost;
	}
	
	public int getTotalCost() {
		return hCost + gCost;
	}

	@Override
	public String toString() {
		return "Node [" + id + "] parent [" + (parent == null ? "null" : parent.getId()) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	
}
