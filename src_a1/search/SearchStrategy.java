package search;

import java.util.List;

import application.View;
import model.State;

public interface SearchStrategy {
	
	public List<State> findPath(State initialBoardState, View view);
	public boolean isGoalState(State state);

}
