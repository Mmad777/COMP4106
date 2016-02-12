package search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.State;

public class BFSStrategy extends SearchStrategy {
	
    Logger logger = LoggerFactory.getLogger(BFSStrategy.class);
	
	protected Node findGoalState(State initState) {

		Set<String> visitedNodes = new HashSet<String>();
		Queue<Node> fringe = new LinkedList<Node>();
		fringe.add(new Node(null, initState));
		
		int iter = 0;
		while (!fringe.isEmpty()) {
			iter++;
			
			// Remove the current node from the queue, and add it to
			// the list of visited nodes (unique Id).
			Node currNode = fringe.remove();
			visitedNodes.add(currNode.getState().getId());
			
			logger.trace("Iteration #{} {}", iter, currNode.toString());
			if (iter % 50000 == 0) logger.info("Iteration #{} - Fringe Size = {} - Visited Size = {}", iter, fringe.size(), visitedNodes.size());
			
			if (isGoalState(currNode.getState())) {
				logger.info("Solved on iteration #{}", iter);
				return currNode;
			}
			
			// Find all children states not yet visited.
			List<State> nonVisitedStates = currNode.getState().generateSuccessors().stream()
				.filter(s -> {
					return !visitedNodes.contains(s.getId());
				}).collect(Collectors.toList());
			
			// If a successor state is the goal state - return that node. Otherwise,
			// add it to the fringe queue.
			for (State nonVisitedState : nonVisitedStates) {
				
				Node newNode = new Node(currNode, nonVisitedState);
				
				if (isGoalState(newNode.getState())) {
					return newNode;
				}
				
				fringe.add(newNode);
				
			}
			
		}
		
		return null;
		
	}

}
