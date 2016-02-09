package application;

import javafx.scene.Scene;
import model.Board;

public class MainController {
	
	private MainView view;
	private Board currentBoard;
	
	public MainController() {
        
		// Initialize the view
		view = new MainView(Board.SIZE);
        
        // Initialize the board and update the view
		currentBoard = new Board();
		view.update(currentBoard);
	}
	
	public Scene getView() {
		return view.getScene();
	}

}
