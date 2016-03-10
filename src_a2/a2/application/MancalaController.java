package a2.application;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import a2.minimax.MiniMaxStrategy;
import a2.minimax.Node;
import a2.model.Board;

public class MancalaController {

	private final static Logger logger = LoggerFactory.getLogger(MancalaController.class);
	
	private Board board;
	private MancalaView view;
	
	public MancalaController() throws NumberFormatException, IOException {
		
		// Initialize state
		board = new Board(6, 6);
		
		// Create and draw the board, initially
		view = new MancalaView(board);
		view.displayBoard();
		
		// Initialize the strategy object
		MiniMaxStrategy strategy = new MiniMaxStrategy();
		
		// Main game loop
		while (!board.isGameOver()) {
			
			int activePlayer = board.getActivePlayer();
			Node node = strategy.miniMaxInit(board, activePlayer);
			int selectedPit = node.getSelectedPit();

			// TODO - Only get selection if player vs. computer, and only on player turn (for player vs. computer)
			//int pitSelection = view.getPitSelection();
			
			boolean moveAgain = board.move(board.getActivePlayer(), selectedPit);
			if (!moveAgain) {
				board.setActivePlayer(board.getActivePlayer() ^ 1);
			}
			
		}
		
		board.emptyStonesIntoMancalas();
		view.displayBoard();
		view.displayWinner(board.determineWinner());
		
	}

}
