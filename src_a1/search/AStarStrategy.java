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

		// use a HashMap<State, Node> and use containsKey
		Set<Node> closed = new HashSet<Node>();
		
		// Use a HashMap<State, Node> and use containsKey
		PriorityQueue<Node> open = 
            new PriorityQueue<Node>(10, new NodeComparator());    
		open.add(new Node(null, initState));
		
		int iter = 0;
		while (!open.isEmpty()) {
			iter++;

			logger.info("Iteration {}", iter);
			
			Node currNode = open.poll();
			// remove from open HashMap
			
			closed.add(currNode);
			// add to closed HashMap instead
			
			// Check if the current node is the goal state
			if (isGoalState(currNode.getState())) {
				logger.info("Solved (current code) on iteration #{}", iter);
				return currNode;
			}

			// Generate all of the successors of the current node
			List<State> successors = currNode.getState().generateSuccessors();
			for (State s : successors) {
				
				// Initialize a new node for the state
				// TODO - Set h cost of newNode
				Node newNode = new Node(currNode, s, currNode.getGCost() + 1);
				
				// Check whether it was previously generated
				if (!open.contains(newNode) && !closed.contains(newNode)) {

					if (isGoalState(newNode.getState())) {
						logger.info("Solved (current code) on iteration #{}", iter);
						return newNode;
					}
					
					// add to open HashMap
					open.add(newNode);					
					continue;
					
				}
				
				// already been added to open
				if (open.contains(newNode)) {
					
					// get the node from the open HashMap
					Node prevNode = findNodeInOpen(open, newNode);
					
					// remove old one from priorityqueue and all of its children
					if (prevNode.getTotalCost() > newNode.getTotalCost()) {
						prevNode.setParent(newNode.getParent());
						prevNode.setGCost(newNode.getGCost());
					}
					
				}
				
				// already been added to closed - continue
				
			}
			
			
		}
		
		return null;
		
	}
	
	private Node findNodeInClosed(Set<Node> closed, Node node) {
		return closed.stream().filter(n -> n.equals(node)).findFirst().get();
	}
	
	private Node findNodeInOpen(PriorityQueue<Node> open, Node node) {
		return open.stream().filter(n -> n.equals(node)).findFirst().get();
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
