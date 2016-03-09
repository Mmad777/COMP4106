package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Game;

public class MancalaController {

	private final static Logger logger = LoggerFactory.getLogger(MancalaController.class);
	
	private Game game;
	private MancalaView view;
	
	public MancalaController() {
		
		game = new Game(6, 6);
		view = new MancalaView(game.getBoard());
		
		// Manually update the display initially
		view.draw();
		
	}

}
