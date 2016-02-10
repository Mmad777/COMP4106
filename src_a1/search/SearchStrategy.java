package search;

import java.util.ArrayList;
import java.util.List;

import model.State;

public abstract class SearchStrategy {
	
	public abstract List<State> search(State initState);
	
	protected List<State> generateGoalPath(Node goalNode) {

		List<State> path = new ArrayList<State>();

		Node currNode = goalNode;
		while (currNode != null) {
			path.add(0, currNode.getState());
			currNode = currNode.getParent();
		}
		
		return path;
		
	}

	protected boolean isGoalState(State state) {
		return state.allPawnsCaptured();
	}

}
