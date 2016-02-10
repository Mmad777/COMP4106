package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.View;
import model.State;

public class BFSStrategy extends SearchStrategy {
	
    Logger logger = LoggerFactory.getLogger(BFSStrategy.class);

	@Override
	public List<State> search(State initState) {
		
		Queue<Node> nodeList = new LinkedList<Node>();
		
		// Create and add a node for the initial board state
		nodeList.add(new Node(null, initState));
		
		int iter = 0;
		while (!nodeList.isEmpty()) {
			
			Node e = nodeList.poll();

			logger.trace("Iteration = " + (iter++));
			logger.trace(e.toString());
			
			if (isGoalState(e.getState())) {
				logger.info("QUIT - reached goal state.");
				break;
			}
			
			// Generate new states
			List<State> states = e.getState().generateSuccessors();
			
			// Add nodes for each state
			for (State s : states) {
				nodeList.add(new Node(e, s));
			}
			
		}
		
		return null;
		
	}
	
	private Node bfs(State initState) {
		
		return null;
		
	}

}
