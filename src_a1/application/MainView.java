package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Board;

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
	
	public void update(Board board) {
		
		board.getBoardEntities().stream().forEach(e -> {
			buttons[e.getX()][e.getY()].setText(e.getIcon());
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
        		btn.setDisable(true);
        		
        		// Disable if it's a border square
        		if (i == 0 || j == 0 || i == (size - 1) || j == (size - 1)) {
            		btn.getStyleClass().add("border");
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
