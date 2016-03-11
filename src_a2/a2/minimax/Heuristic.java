package a2.minimax;

import java.util.stream.Stream;

import a2.model.Board;

public interface Heuristic {

	public int evaluate(Board board, int player);
	
	public class MancalaCountHeuristic implements Heuristic {

		@Override
		public int evaluate(Board board, int player) {
			return board.getMancalas()[player].getStones();
		}
		
	}
	
	public class StoneCountHeuristic implements Heuristic {

		@Override
		public int evaluate(Board board, int player) {
			return Stream.of(board.getPits()[player]).mapToInt(p -> p.getStones()).sum();
		}
		
	}

}
