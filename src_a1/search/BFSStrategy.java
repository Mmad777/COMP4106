package search;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.BoardState;

public class BFSStrategy implements SearchStrategy {

	@Override
	public List<BoardState> findPath(BoardState initialBoardState) {
		
		Queue<Node> nodeList = new LinkedList<Node>();
		
		// Create and add a node for the initial board state
		Node initialNode = new Node(null, initialBoardState);
		nodeList.add(initialNode);
		
		
		return null;
		
	}

}
