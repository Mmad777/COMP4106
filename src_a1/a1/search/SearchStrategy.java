package a1.search;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import a1.model.State;

public abstract class SearchStrategy {
	
	private Logger logger = LoggerFactory.getLogger(SearchStrategy.class);
	
	protected abstract Node findGoalState(State initState);
	
	protected int goalStateIteration;
	
	public List<State> findGoalPath(State initState) {

		Node goalNode = findGoalState(initState);
		List<State> goalPath = goalNode != null ? generateGoalPath(goalNode) : null;
		return goalPath;
		
	}
	
	public int getGoalStateIteration() {
		return goalStateIteration;
	}
	
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
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
