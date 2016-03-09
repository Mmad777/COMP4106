package application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Board;

public class MancalaController {

	private final static Logger logger = LoggerFactory.getLogger(MancalaController.class);
	
	private Board board;
	private MancalaView view;
	
	public MancalaController() throws NumberFormatException, IOException {
		
		board = new Board(6, 6);
		view = new MancalaView(board);
		
		// Draw the board initially
		view.displayBoard();
		
		// Main game loop
		while (!board.isGameOver()) {
			
			int pitSelection = view.getPitSelection();
			boolean moveAgain = board.move(board.getActivePlayer(), pitSelection);
			if (!moveAgain) {
				board.toggleActivePlayer();
			}
			
		}
		
		board.emptyStonesIntoMancalas();
		view.displayBoard();
		view.displayWinner(board.determineWinner());
		
	}

}
