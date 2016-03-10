package a1.search;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import a1.model.State;

public class AStarStrategy extends SearchStrategy {

	private Logger logger = LoggerFactory.getLogger(AStarStrategy.class);
	
	//private Heuristic heuristic = new Heuristic.PawnCountHeuristic();
	//private Heuristic heuristic = new Heuristic.PawnDistanceHeuristic();
	//private Heuristic heuristic = new Heuristic.AverageHeuristic();
	
	private Heuristic heuristic = new Heuristic.ClosestPawnHeuristic();

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
				goalStateIteration = iter;
				return currNode;
			}

			// Generate all of the successors of the current node
			List<State> successors = currNode.getState().generateSuccessors();
			for (State s : successors) {
				
				// Skip if state is already in closed list
				if (closed.containsKey(s)) {
					continue;
				}
				
				Node newNode = new Node(currNode, s, currNode.getGCost() + 1, heuristic.evaluate(s));
				
				// Check whether it was previously generated
				if (!openMap.containsKey(s)) {

					if (isGoalState(newNode.getState())) {
						goalStateIteration = iter;
						return newNode;
					}
					
					openMap.put(s, newNode);
					open.add(newNode);					
					continue;
					
				}
				
				// TODO - Update costs of previously generated nodes...
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

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + heuristic.getClass().getSimpleName() + "]";
	}
	
	private class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node n1, Node n2) {			
			return n1.getTotalCost() - n2.getTotalCost();			
		}
		
	}

}
