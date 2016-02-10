package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import application.View;
import model.State;

public class BFSStrategy implements SearchStrategy {

	@Override
	public List<State> findPath(State initialBoardState, View view) {
		
		int minPawns = 5;
		Queue<Node> nodeList = new LinkedList<Node>();
		
		// Create and add a node for the initial board state
		nodeList.add(new Node(null, initialBoardState));
		
		int iter = 0;
		while (true) {
			
			// Check if there are no more elements
			if (nodeList.isEmpty()) {
				System.out.println("QUIT - nodeList is empty.");
				break;
			}
			
			// Get the first element in the queue
			Node e = nodeList.poll();

			System.out.println("\niteration = " + iter);
			System.out.println("knight pos = " + e.getState().getKnight().getPosition());
			System.out.println(e);
			
			if (e.getState().getPawns().size() < minPawns) {
				minPawns = e.getState().getPawns().size();
			}
			
			// Display it
			view.draw(e.getState());
//			try {
//				Thread.sleep(30);
//			} catch (InterruptedException ex) {
//				ex.printStackTrace();
//			}
			
			// Check if we've reached the goal state
			if (isGoalState(e.getState())) {
				System.out.println("QUIT - reached goal state.");
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
