package application;

import java.io.IOException;

public class MancalaApplication {

	public static void main(String[] args) {
		
        try {
			new MancalaController();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
        
	}

}
