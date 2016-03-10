package a2.minimax;

import java.util.ArrayList;
import java.util.List;

import a2.model.Board;

public class MiniMaxStrategy {
	
	public Node miniMax(Node node, int depth, boolean max, int player) {
		
		if (depth == 0 || node.getState().isGameOver()) {
			return node;
		}
		
		if (max) {
			
			Node bestNode = null;
			for (Node successor : generateSuccessors(node, player)) {
				Node v = miniMax(successor, depth - 1, false, player ^ 1);
				bestNode = max(bestNode, v);
			}			
			return bestNode;
			
		} else {
			
			Node bestNode = null;
			for (Node successor : generateSuccessors(node, player)) {
				Node v = miniMax(successor, depth - 1, true, player ^ 1);
				bestNode = min(bestNode, v);
			}			
			return bestNode;
			
		}
		
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
			child.move(player, i);
			successors.add(new Node(node, child, heuristicOne(child, player)));
			
		}
		
		return successors;
		
	}
	
	private int heuristicOne(Board board, int player) {
		return board.getMancalas()[player].getStones();
	}

}
