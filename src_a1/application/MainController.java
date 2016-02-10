package application;

import model.State;

public class MainController {
	
	private MainView view;
	private State currentBoard;
	
	public MainController() {
        
		// Initialize the view
		view = new MainView(State.SIZE);
        
        // Initialize the board and update the view
		currentBoard = new State(true);
		view.draw(currentBoard);
		
	}

}
