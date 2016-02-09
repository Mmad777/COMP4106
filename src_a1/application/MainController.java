package application;

import java.util.Arrays;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.BoardEntity;
import model.BoardEntity.Position;
import model.BoardState;
import model.Knight;

public class MainController {
	
	private MainView view;
	private BoardState currentBoard;
	
	public MainController() {
        
		// Initialize the view
		view = new MainView(BoardState.SIZE);
        
        // Initialize the board and update the view
		currentBoard = new BoardState();
		view.update(currentBoard);
		
		// Add handlers for grid buttons
		//addButtonHandlers();
		
	}
	
	public Scene getView() {
		return view.getScene();
	}
	
	private void onBoardEntityClicked(BoardEntity boardEntity) {
		
		if (!(boardEntity instanceof Knight)) {
			return;
		}
		
		List<Position> possibleMoves = boardEntity.getPossibleMoves();
		view.updatePossibleMoves(possibleMoves);
		
	}
	
	private void addButtonHandlers() {
		
		Button[][] buttons = view.getButtons();
        for(int i=0; i<buttons.length; i++){
        	for(int j=0; j<buttons.length;j++){
				
        		int x = i, y = j;
        		Button btn = buttons[i][j];
				btn.setOnAction(e -> {
					
					try {
						BoardEntity boardEntity = currentBoard.getBoardEntity(x, y);
						onBoardEntityClicked(boardEntity);
						
					} catch (Exception ex) {
						// Do nothing
						
					}
					
				});
        		
        	}
        }
		
	}

}
