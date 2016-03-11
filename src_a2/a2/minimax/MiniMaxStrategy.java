package a2.minimax;

import java.util.ArrayList;
import java.util.List;

import a2.model.Board;

public class MiniMaxStrategy {
	
	private static final int MAX_DEPTH = 4;

	private Heuristic heuristic = new Heuristic.MancalaCountHeuristic();
	//private Heuristic heuristic = new Heuristic.StoneCountHeuristic();
	
	public int miniMax(Board board, int player) {
		
		Node rootNode = new Node(null, board);
		Node lastNode = miniMax(rootNode, MAX_DEPTH, player);
		
		Node currNode = lastNode;
		while (currNode != null) {
			
			if (currNode.getParent() == rootNode) {
				return currNode.getSelectedPit();
			}
			
			currNode = currNode.getParent();
			
		}
		
		return -1;
		
	}
	
	public Node miniMax(Node node, int depth, int player) {
		
		if (depth == 0 || node.getState().isGameOver()) {
			return node;
		}
		
		// Don't always alternate if the next move is the same player
		boolean max = node.getParent() == null || node.getParent().getState().getActivePlayer() == player;
		
		Node bestNode = null;
		for (Node successor : generateSuccessors(node, player)) {
			Node v = miniMax(successor, depth - 1, successor.getState().getActivePlayer());			
			bestNode = max ? max(bestNode, v) : min(bestNode, v);			
		}
		
		return bestNode;
		
	}
	
	private Node min(Node bestNode, Node node) {
		return bestNode != null && bestNode.gethVal() < node.gethVal() ? bestNode : node;
	}
	
	private Node max(Node bestNode, Node node) {
		return bestNode != null && bestNode.gethVal() > node.gethVal() ? bestNode : node;
	}
	
	private List<Node> generateSuccessors(Node node, int player) {
		
		List<Node> successors = new ArrayList<Node>();
		Board parent = node.getState();
		
		for (int i=0; i<parent.getSize(); i++) {
			
			Board child = new Board(parent);
			
			// Skip empty pits
			if (child.getPits()[player][i].isEmpty()) continue;
			
			// Check if the player can move again, and set the player accordingly
			boolean moveAgain = child.move(player, i, false);			
			child.setActivePlayer(moveAgain ? player : player ^ 1);
			
			// Initialize a child node and add it to the list
			Node childNode = new Node(node, child, i, heuristic.evaluate(child, player));			
			successors.add(childNode);
			
		}
		
		return successors;
		
	}

}
