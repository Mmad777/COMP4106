package application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Game;

public class MancalaController {

	private final static Logger logger = LoggerFactory.getLogger(MancalaController.class);
	
	private Game game;
	private MancalaView view;
	
	public MancalaController() throws NumberFormatException, IOException {
		
		game = new Game(6, 6);
		view = new MancalaView(game.getBoard());
		
		// Draw the board initially
		view.displayBoard();
		
		// Main game loop
		while (!game.getBoard().isGameOver()) {
			
			int pitSelection = view.getPitSelection();
			if (!game.getBoard().move(game.getBoard().getActivePlayer(), pitSelection)) {
				game.getBoard().toggleActivePlayer();
			}
			
		}
		
		game.getBoard().emptyStonesIntoMancalas();
		view.displayBoard();
		view.displayWinner(game.determineWinner());
		
	}

}
