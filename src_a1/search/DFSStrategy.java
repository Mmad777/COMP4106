package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.View;
import model.State;

public class DFSStrategy implements SearchStrategy {
	
    Logger logger = LoggerFactory.getLogger(DFSStrategy.class);
    
    private HashMap<String, Node> visitedNodes;
    private Node goalNode;

	@Override
	public List<State> search(State initState, View view) {
		
		visitedNodes = new HashMap<String, Node>();
		Node initialNode = new Node(null, initState);
		
		dfs(initialNode);
		logger.info("Done!");
		
		if (goalNode != null) {

			List<State> path = new ArrayList<State>();
			
			Node currNode = goalNode;
			while (currNode != null) {
				path.add(0, currNode.getState());
				currNode = currNode.getParent();
			}
			
			view.drawStatesWithDelay(path);
			
		}
		
		return null;
		
	}
	
	private boolean dfs(Node node) {
		
		if (node == null) {
			logger.warn("node == null");
			return false;
		}
		
		State state = node.getState();
		String id = state.getId();
		
		// Check if the node was already visited
		if (visitedNodes.containsKey(id)) {
			logger.info("Already visited state: " + id);
			return false;
		}
		
		// Add node to visited
		visitedNodes.put(id, node);
		
		// Check if node is goal state
		if (isGoalState(state)) {
			logger.info("Found goal state: " + id);
			goalNode = node;
			return true;
		}
		
		// Generate successors
		List<State> childStates = node.getState().generate();
		if (childStates.size() == 0) {
			logger.info("No more child states");
			return false;
		}
		
		for (State childState : childStates) {
			boolean result = dfs(new Node(node, childState));
			if (result == true) return true;
		}
		
		return false;
		
	}

	@Override
	public boolean isGoalState(State state) {
		return state.allPawnsCaptured();
	}

}
