package a1.search;

import a1.model.Knight;
import a1.model.Pawn;
import a1.model.State;

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
			
			int distance = 0;
			for (Pawn p : state.getPawns()) {
				distance += Heuristic.calculateDistance(state.getKnight(), p);				
			}
			
			return distance;
			
		}
		
	}
	
	public class ClosestPawnHeuristic implements Heuristic {

		@Override
		public int evaluate(State state) {
			
			double minDistance = 0;
			for (Pawn p : state.getPawns()) {
				
				double distance = Heuristic.calculateDistance(state.getKnight(), p);
				if (minDistance == 0 || distance < minDistance) {
					minDistance = distance;
				}
				
			}
			
			return (int) minDistance;
			
		}
		
	}
	
	public class AverageHeuristic implements Heuristic {

		@Override
		public int evaluate(State state) {
			
			int pawnCount = new PawnCountHeuristic().evaluate(state);
			int pawnDistance = new PawnDistanceHeuristic().evaluate(state);
			return (pawnCount + pawnDistance) / 2;
			
		}
		
		
	}
	
	static int calculateDistance(Knight knight, Pawn pawn) {
		
		int knightX = knight.getPosition().getX();
		int knightY = knight.getPosition().getY();

		double diffX = Math.pow(pawn.getPosition().getX() - knightX, 2);
		double diffY = Math.pow(pawn.getPosition().getY() - knightY, 2);
		double distance = Math.sqrt(diffX + diffY);
		
		return (int) distance;
		
	}

}
