package application;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.BoardEntity.Position;
import model.BoardState;

public class MainView {
	
	private int size;
	
	private Scene scene;
	private GridPane gridPane;
	private Button[][] buttons;
	
	public MainView(int size) {
		this.size = size;
		init();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Button[][] getButtons() {
		return buttons;
	}
	
	public void update(BoardState board) {
		
		board.getBoardEntities().stream().forEach(e -> {
			Button btn = buttons[e.getPosition().getX()][e.getPosition().getY()];
			btn.setText(e.getIcon());
		});
	
	}
	
	public void updatePossibleMoves(List<Position> possibleMoves) {
		
		possibleMoves.stream().forEach(p -> {
			buttons[p.getX()][p.getY()].getStyleClass().add("valid-move");
		});
		
	}
	
	private void init() {
        
		// Create a GridPane and 2D-array of Button
        gridPane = new GridPane();
        buttons = new Button[this.size][this.size];
        
        for(int i=0; i<buttons.length; i++){
        	for(int j=0; j<buttons.length;j++){
        		
        		// Initialize button
        		Button btn = new Button("");
        		btn.setPrefSize(50, 50); 
        		btn.getStyleClass().add("board-button");
        		
        		// Disable if it's a border square
        		if (i == 0 || j == 0 || i == (size - 1) || j == (size - 1)) {
            		btn.getStyleClass().add("border");
            		btn.setDisable(true);
        		}
        		
        		// Add to the grid, and array
        		gridPane.add(btn, i, j);  
        		buttons[i][j] = btn;
        		
	       	}
        }
                
        //Adding GridPane to the scene
    	scene = new Scene(gridPane);
    	scene.getStylesheets().add("application/stylesheet.css");
		
	}

}
