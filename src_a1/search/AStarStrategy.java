package search;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.State;

public class AStarStrategy extends SearchStrategy {

	Logger logger = LoggerFactory.getLogger(AStarStrategy.class);

	@Override
	protected Node findGoalState(State initState) {
		
		Map<State, Node> closed = new HashMap<State, Node>();
		
		PriorityQueue<Node> open = 
            new PriorityQueue<Node>(10, new NodeComparator());    
		Map<State, Node> openMap = new HashMap<State, Node>();
		
		// Initialize root node, and add to open
		Node initNode = new Node(null, initState);
		open.add(initNode);
		openMap.put(initState, initNode);
		
		int iter = 0;
		while (!open.isEmpty()) {
			iter++;
			
			Node currNode = open.poll();
			openMap.remove(currNode.getState(), currNode);
			
			if (!closed.containsKey(currNode.getState())) {
				closed.put(currNode.getState(), currNode);
			}			
			
			// Check if the current node is the goal state
			if (isGoalState(currNode.getState())) {
				logger.info("Solved (current code) on iteration #{}", iter);
				return currNode;
			}

			// Generate all of the successors of the current node
			List<State> successors = currNode.getState().generateSuccessors();
			for (State s : successors) {
				
				// Skip if state is already in closed list
				if (closed.containsKey(s)) {
					continue;
				}
				
				Node newNode = new Node(currNode, s, currNode.getGCost() + 1);
				
				// Check whether it was previously generated
				if (!openMap.containsKey(s)) {

					if (isGoalState(newNode.getState())) {
						logger.info("Solved (current code) on iteration #{}", iter);
						return newNode;
					}
					
					openMap.put(s, newNode);
					open.add(newNode);					
					continue;
					
				}
				
//					Node prevNode = openMap.get(s);
//					
//					// remove old one from priorityqueue and all of its children
//					if (prevNode.getTotalCost() > newNode.getTotalCost()) {
//						prevNode.setParent(newNode.getParent());
//						prevNode.setGCost(newNode.getGCost());
//					}
				
			}
			
			
		}
		
		return null;
		
	}
	
	private class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node n1, Node n2) {
			
			if (n1.getTotalCost() < n2.getTotalCost()) return -1;
			if (n1.getTotalCost() > n2.getTotalCost()) return 1;
			
			return 0;
			
		}
		
	}

	private int heuristic(State state) {
		return 0;
	}

}
