package a2.minimax;

import java.util.ArrayList;
import java.util.List;

import a2.model.Board;

public class MiniMaxStrategy {
	
	private static final boolean USE_AB = true;
	private static final int MAX_DEPTH = 7;

	private Heuristic heuristic0 = new Heuristic.MancalaCountHeuristic();
	private Heuristic heuristic1 = new Heuristic.StoneCountHeuristic();
	
	private int totalNodeCount = 0;
	private int nodeCount = 0;
	
	public int getTotalNodeCount() {
		return totalNodeCount;
	}
	public int getNodeCount() {
		return nodeCount;
	}
	
	public int miniMax(Board board, int player) {
		
		nodeCount = 0;
		
		Node rootNode = new Node(null, board);		
		Node lastNode = USE_AB ? 
				miniMaxAB(rootNode, -1000000, 1000000, MAX_DEPTH, player) : 
					miniMax(rootNode, MAX_DEPTH, player);
		
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
		
		nodeCount++;
		totalNodeCount++;
		
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
	
	public Node miniMaxAB(Node node, int alpha, int beta, int depth, int player) {
		
		nodeCount++;
		totalNodeCount++;
		
		if (depth == 0 || node.getState().isGameOver()) {
			return node;
		}
		
		boolean max = node.getParent() == null || node.getParent().getState().getActivePlayer() == player;		

		Node bestNode = null;
		for (Node successor : generateSuccessors(node, player)) {
			
			Node v = miniMaxAB(successor, alpha, beta, depth - 1, successor.getState().getActivePlayer());		
			
			if (max) {
				bestNode = max(bestNode, v);
				alpha = max(alpha, bestNode.gethVal());				
				if (beta <= alpha) break;
				
			} else {
				bestNode = min(bestNode, v);
				beta = min(beta, bestNode.gethVal());
				if (beta >= alpha) break;
				
			}
			
		}
		
		return bestNode;
		
	}

	private int max(int alpha, int v) {
		return alpha > v ? alpha : v;
	}
	
	private int min(int beta, int v) {
		return beta < v ? beta : v;
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
			if (player == 0) {
				successors.add(new Node(node, child, i, heuristic0.evaluate(child, player)));
			} else {
				successors.add(new Node(node, child, i, heuristic1.evaluate(child, player)));			
			}
			
		}
		
		return successors;
		
	}
}
