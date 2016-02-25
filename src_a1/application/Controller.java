package application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.State;
import search.AStarStrategy;
import search.BFSStrategy;
import search.DFSStrategy;
import search.SearchStrategy;

public class Controller {

	Logger logger = LoggerFactory.getLogger(Controller.class);
	
	private View view;
	private State initialState;
	
	public Controller() {
        
		// Initialize the view
		view = new View(State.BOARD_SIZE);
        
        // Initialize the board and draw the initial view
		initialState = new State(true, false);
		view.draw(true, initialState);
		
		// Pick a search strategy
		//SearchStrategy strategy = new BFSStrategy();
		//SearchStrategy strategy = new DFSStrategy();
		SearchStrategy strategy = new AStarStrategy();
		
		// Run the search
		List<State> path = strategy.findGoalPath(initialState);		
		if (path != null) {
			view.drawStates(path);
			log(strategy, path);
		}
		
	}
	
	private void log(SearchStrategy strategy, List<State> path) {
		
		System.out.println();
		logger.info("Size \t= {}x{}", State.BOARD_SIZE, State.BOARD_SIZE);
		logger.info("# Pawns \t= {}", State.NUM_PAWNS);
		logger.info("Strategy \t= {}", strategy);
		logger.info("Goal Path \t= {} states", path.size());
		logger.info("Iteration \t= {}", strategy.getGoalStateIteration());
		
		
	}

}
