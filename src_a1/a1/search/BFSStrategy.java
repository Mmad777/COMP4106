package a1.search;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import a1.model.State;

public class BFSStrategy extends SearchStrategy {
	
    Logger logger = LoggerFactory.getLogger(BFSStrategy.class);
	
	protected Node findGoalState(State initState) {

		Set<String> visitedNodes = new HashSet<String>();
		Queue<Node> fringe = new LinkedList<Node>();
		fringe.add(new Node(null, initState));
		
		int iter = 0;
		while (!fringe.isEmpty()) {
			iter++;			
			logger.trace("Iteration #{}", iter);
			
			Node currNode = fringe.remove();
			boolean added = visitedNodes.add(currNode.getState().getId());
			if (!added) {
				continue;
			}
			
			
			// Check if the current node is the goal state
			if (isGoalState(currNode.getState())) {
				goalStateIteration = iter;
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
					goalStateIteration = iter;
					return newNode;
				}				
				fringe.add(newNode);
				
			}
			
		}
		
		return null;
		
	}

}
