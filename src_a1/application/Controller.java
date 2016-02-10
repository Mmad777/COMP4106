package application;

import model.State;
import search.DFSStrategy;

public class Controller {
	
	private View view;
	private State initialState;
	
	public Controller() {
        
		// Initialize the view
		view = new View(State.SIZE);
        
        // Initialize the board and update the view
		initialState = new State(true);
		view.draw(initialState);
		
		//BFSStrategy strategy = new BFSStrategy();
		DFSStrategy strategy = new DFSStrategy();
		strategy.search(initialState, view);
		
	}

}
