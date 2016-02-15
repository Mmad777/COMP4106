package application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.State;
import search.AStarStrategy;
import search.BFSStrategy;
import search.DFSStrategy;

public class Controller {

	Logger logger = LoggerFactory.getLogger(Controller.class);
	
	private View view;
	private State initialState;
	
	public Controller() {
        
		// Initialize the view
		view = new View(State.BOARD_SIZE);
        
        // Initialize the board and draw the initial view
		initialState = new State(true);
		view.draw(true, initialState);
		
		// Pick a search strategy
		//BFSStrategy strategy = new BFSStrategy();
		//DFSStrategy strategy = new DFSStrategy();
		AStarStrategy strategy = new AStarStrategy();
		
		// Run the search
		List<State> path = strategy.findGoalPath(initialState);		
		if (path == null) {
			logger.info("No path found");
		}

		// Display all of the states along the path
		view.drawStates(path);
		
	}

}
