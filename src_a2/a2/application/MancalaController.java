package a2.application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import a2.model.Board;

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
			
			// TODO - Only get selection if player vs. computer, and only on player turn
			int pitSelection = view.getPitSelection();
			boolean moveAgain = board.move(board.getActivePlayer(), pitSelection);
			if (!moveAgain) {
				board.setActivePlayer(board.getActivePlayer() ^ 1);
			}
			
		}
		
		board.emptyStonesIntoMancalas();
		view.displayBoard();
		view.displayWinner(board.determineWinner());
		
	}

}
