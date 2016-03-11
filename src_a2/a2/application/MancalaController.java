package a2.application;

import java.io.IOException;

import a2.minimax.MiniMaxStrategy;
import a2.model.Board;

public class MancalaController {
	
	private enum GameType {
		PLAYER_VS_COMPUTER,
		COMPUTER_VS_COMPUTER	
	}
	
	private final int PLAYER_NUM 	= 0;
	//private GameType gameType 		= GameType.PLAYER_VS_COMPUTER;
	private GameType gameType 		= GameType.COMPUTER_VS_COMPUTER;
	
	private Board board;
	private MancalaView view;
	
	public MancalaController() throws NumberFormatException, IOException {
		
		// Initialize state
		board = new Board(6, 6);
		
		// Create and draw the board, initially
		view = new MancalaView(board);
		view.displayBoard(true);
		
		// Initialize the strategy object
		MiniMaxStrategy strategy = new MiniMaxStrategy();
		
		// Main game loop
		int turn = 0;
		while (!board.isGameOver()) {
			
			int activePlayer = board.getActivePlayer();
			
			int selectedPit = -1;
			if (gameType == GameType.PLAYER_VS_COMPUTER && activePlayer == PLAYER_NUM) {
				selectedPit = view.getPitSelection();
			} else {
				selectedPit  = strategy.miniMax(board, activePlayer);
				System.out.println("Nodes visited: " + strategy.getNodeCount());
			}
			
			view.displayMove(turn++, activePlayer, selectedPit);
			
			boolean moveAgain = board.move(board.getActivePlayer(), selectedPit, true);
			if (!moveAgain) {
				board.setActivePlayer(board.getActivePlayer() ^ 1);
			}
			
		}
		
		System.out.println("Total nodes visited: " + strategy.getTotalNodeCount());
		
		view.displayGameOver();
		board.emptyStonesIntoMancalas();
		view.displayBoard(false);
		view.displayWinner(board.determineWinner());
		
	}

}
