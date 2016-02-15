package search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.State;

public class AStarStrategy extends SearchStrategy {

	Logger logger = LoggerFactory.getLogger(AStarStrategy.class);

	@Override
	protected Node findGoalState(State initState) {
		
		Set<String> visitedNodes = new HashSet<String>();
        PriorityQueue<Node> fringe = 
            new PriorityQueue<Node>(10, new NodeComparator());    
		fringe.add(new Node(null, initState));
		
		int iter = 0;
		while (!fringe.isEmpty()) {
			iter++;
			
			Node currNode = fringe.poll();
			boolean added = visitedNodes.add(currNode.getState().getId());
			if (!added) {
				continue;
			}
			
			// Check if the current node is the goal state
			if (isGoalState(currNode.getState())) {
				logger.info("Solved (current code) on iteration #{}", iter);
				return currNode;
			}

			// Add nodes for all children states not yet visited
			List<State> successors = currNode.getState().generateSuccessors();
			
			for (State s : successors) {
				
				Node newNode = new Node(currNode, s, currNode.getCost() + 1);
				if (isGoalState(newNode.getState())) {
					logger.info("Solved on iteration #{}", iter);
					return newNode;
				}
				
				String id = s.getId();
				if (!visitedNodes.contains(id) && !fringe.contains(id)) {
					fringe.add(newNode);
				}
				
			}
			
			
		}
		
		return null;
		
	}
	
	private class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node n1, Node n2) {
			
			int s1e = evaluate(n1);
			int s2e = evaluate(n2);
			
			if (s1e < s2e) return -1;
			if (s1e > s2e) return 1;
			
			return 0;
			
		}
		
	}
	
	private int evaluate(Node node) {
		return node.getCost() + heuristic(node.getState());
	}

	private int heuristic(State state) {
		return 0;
	}

}
