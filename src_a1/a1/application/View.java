package a1.application;

import java.util.List;

import a1.model.Position;
import a1.model.State;

public class View {
	
	private final boolean USE_DELAY = false;
	private final int DELAY = 10;
	
	private int size;
	
	public View(int size) {
		this.size = size;
	}
	
	public void drawStates(List<State> states) {

		states.stream().forEach(s -> {
			
			draw(false, s);

			if (USE_DELAY) {
				
				try {
					Thread.sleep(DELAY);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			
			}
			
		});
		
	}
	
	public void draw(boolean initial, State state) {
		
		String[][] stateDisplay = new String[size][size];
		
		state.getPawns().stream().forEach(e -> {
			int x = e.getPosition().getX();
			int y = e.getPosition().getY();
			stateDisplay[x][y] = e.getIcon();
		});
		
		Position kPos = state.getKnight().getPosition();
		stateDisplay[kPos.getX()][kPos.getY()] = state.getKnight().getIcon();

		System.out.println();
		
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				
				System.out.print("[");
				
				String s = stateDisplay[i][j] == null ? "_" : stateDisplay[i][j];
				if (i == 0 || i == size - 1 || j == 0 || j == size - 1) s = "X";
				System.out.print(s);
				
				System.out.print("]");
				
			}
			
			System.out.println();
			
		}
	
	}

}
