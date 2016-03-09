package util;

import java.util.Random;

public class MathUtil {
	
	public static int randomInt(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max + 1) - min) + min;		
	}

}
