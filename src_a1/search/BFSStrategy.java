package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.View;
import model.State;

public class BFSStrategy implements SearchStrategy {
	
    Logger logger = LoggerFactory.getLogger(BFSStrategy.class);
	
	private final boolean 	SLEEP = false;
	private final int 		SLEEP_TIME = 30;

	@Override
	public List<State> findPath(State initialBoardState, View view) {
		
		Queue<Node> nodeList = new LinkedList<Node>();
		
		// Create and add a node for the initial board state
		nodeList.add(new Node(null, initialBoardState));
		
		int iter = 0;
		while (true) {
			
			iter++;
			// Check if there are no more elements
			if (nodeList.isEmpty()) {
				logger.info("QUIT - nodeList is empty.");
				break;
			}
			
			// Get the first element in the queue
			Node e = nodeList.poll();

			// Log some information
			System.out.println("\nIteration = " + iter);
			System.out.println(e.toString());
			
			// Display the state
			view.draw(e.getState());
			
			// Allow for a delay so that states/program flow can be viewed 
			if (SLEEP) {
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			
			// Check if we've reached the goal state
			if (isGoalState(e.getState())) {
				logger.info("QUIT - reached goal state.");
				break;
			}
			
			// Generate new states
			List<State> states = e.getState().generate();
			
			// Add nodes for each state
			for (State s : states) {
				nodeList.add(new Node(e, s));
			}
			
		}
		
		return null;
		
	}

	@Override
	public boolean isGoalState(State state) {
		return state.isFinished();
	}

}
