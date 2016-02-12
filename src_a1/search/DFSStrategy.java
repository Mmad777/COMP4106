package search;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.State;

public class DFSStrategy extends SearchStrategy {

	Logger logger = LoggerFactory.getLogger(DFSStrategy.class);

	protected Node findGoalState(State initState) {

		Set<String> visitedNodes = new HashSet<String>();
		Stack<Node> fringe = new Stack<Node>();
		fringe.add(new Node(null, initState));

		int iter = 0;
		while (!fringe.isEmpty()) {
			iter++;

			Node currNode = fringe.pop();
			visitedNodes.add(currNode.getState().getId());
			
			if (isGoalState(currNode.getState())) {
				logger.info("Solved on iteration #{}", iter);
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

}
