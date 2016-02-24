package search;

import model.Pawn;
import model.State;

public interface Heuristic {
	
	public int evaluate(State state);
	
	public class PawnCountHeuristic implements Heuristic {

		@Override
		public int evaluate(State state) {
			return state.getPawns().size();
		}
		
	}
	
	public class PawnDistanceHeuristic implements Heuristic {

		@Override
		public int evaluate(State state) {
			
			int knightX = state.getKnight().getPosition().getX();
			int knightY = state.getKnight().getPosition().getY();
			
			int distance = 0;
			for (Pawn p : state.getPawns()) {

				double diffX = Math.pow(p.getPosition().getX() - knightX, 2);
				double diffY = Math.pow(p.getPosition().getY() - knightY, 2);
				distance += Math.sqrt(diffX + diffY);
				
			}
			
			return distance;
			
		}
		
	}
	
	public class ClosestPawnHeuristic implements Heuristic {

		@Override
		public int evaluate(State state) {
			
			int knightX = state.getKnight().getPosition().getX();
			int knightY = state.getKnight().getPosition().getY();
			
			double minDistance = 0;
			for (Pawn p : state.getPawns()) {

				double diffX = Math.pow(p.getPosition().getX() - knightX, 2);
				double diffY = Math.pow(p.getPosition().getY() - knightY, 2);
				double distance = Math.sqrt(diffX + diffY);
				
				if (minDistance == 0 || distance < minDistance) {
					minDistance = distance;
				}
				
			}
			
			return (int) minDistance;
			
		}
		
	}

}
