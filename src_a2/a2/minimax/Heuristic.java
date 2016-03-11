package a2.minimax;

import a2.model.Board;

public interface Heuristic {

	public int evaluate(Board board, int player);
	
	public class MancalaCountHeuristic implements Heuristic {

		@Override
		public int evaluate(Board board, int player) {
			return board.getMancalas()[player].getStones();
		}
		
	}

}
