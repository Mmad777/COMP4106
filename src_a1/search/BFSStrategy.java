package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.State;

public class BFSStrategy implements SearchStrategy {

	@Override
	public List<State> findPath(State initialBoardState) {
		
		Queue<Node> nodeList = new LinkedList<Node>();
		
		// Create and add a node for the initial board state
		nodeList.add(new Node(null, initialBoardState));
		
		while (true) {
			
			// No more elements
			if (nodeList.isEmpty()) {
				System.out.println("QUIT - nodeList is empty.");
				break;
			}
			
			Node e = nodeList.poll();
			
			// Reached goal state
			if (isGoalState(e.getState())) {
				System.out.println("QUIT - reached goal state.");
				break;
			}
			
			// Generate new states
			
			
			
		}
		
		return null;
		
	}

	@Override
	public boolean isGoalState(State state) {
		return state.isFinished();
	}

}
