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

	@Override
	public List<State> search(State initState) {

		Node goalNode = bfs(initState);
		return goalNode != null ? generateGoalPath(goalNode) : null;
		
	}
	
	private Node bfs(State initState) {

		Set<String> visitedNodes = new HashSet<String>();
		Queue<Node> fringe = new LinkedList<Node>();
		fringe.add(new Node(null, initState));
		
		int iter = 0;
		while (!fringe.isEmpty()) {
			iter++;
			
			Node currNode = fringe.remove();
			visitedNodes.add(currNode.getState().getId());
			
			logger.trace("Iteration #{} {}", iter, currNode.toString());
			if (iter % 50000 == 0) logger.info("Iteration #{} - Fringe Size = {} - Visited Size = {}", iter, fringe.size(), visitedNodes.size());
			
			if (isGoalState(currNode.getState())) {
				logger.info("Solved on iteration #{}", iter);
				return currNode;
			}
			
			// Add nodes for all children states not yet visited			
			List<State> nonVisitedStates = currNode.getState().generateSuccessors().stream()
				.filter(s -> {
					return !visitedNodes.contains(s.getId());
				}).collect(Collectors.toList());
			
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
	
	private void log(Node currNode) {
		
	}

}
