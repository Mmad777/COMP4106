package search;

import java.util.List;

import model.BoardState;

public interface SearchStrategy {
	
	public List<BoardState> findPath(BoardState initialBoardState);
	public boolean isGoalState(BoardState state);

}
