package search;

import java.util.List;

import model.State;

public interface SearchStrategy {
	
	public List<State> findPath(State initialBoardState);
	public boolean isGoalState(State state);

}
