package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.View;
import model.State;

public class DFSStrategy implements SearchStrategy {

	Logger logger = LoggerFactory.getLogger(DFSStrategy.class);
	
	@Override
	public List<State> search(State initState, View view) {

		// Run the DFS algorithm
		Node goalNode = dfs(initState);

		// If a goal state has been reached...
		if (goalNode != null) {

			List<State> path = new ArrayList<State>();

			// Generate the list of states along the goal path, in correct order
			Node currNode = goalNode;
			while (currNode != null) {
				path.add(0, currNode.getState());
				currNode = currNode.getParent();
			}

			view.drawStatesWithDelay(path);

		}

		return null;

	}

	private Node dfs(State initState) {

		// Store visited node IDs
		Set<String> visitedNodes = new HashSet<String>();

		Stack<Node> fringe = new Stack<Node>();
		fringe.add(new Node(null, initState));

		while (!fringe.isEmpty()) {

			Node currNode = fringe.pop();
			visitedNodes.add(currNode.getState().getId());

			if (isGoalState(currNode.getState())) {
				return currNode;
			}

			// Add nodes for all children states not yet visited
			currNode.getState().generateSuccessors().stream()
				.filter(s -> {
					return !visitedNodes.contains(s.getId());
				})
				.forEach(s -> {
					fringe.push(new Node(currNode, s));
				});

		}
		
		return null;

	}

	@Override
	public boolean isGoalState(State state) {
		return state.allPawnsCaptured();
	}

}
