package a3.classifiers;

public class ClassificationResults {
	
	private int numCorrect;
	private int numIncorrect;
	
	public ClassificationResults(int numCorrect, int numIncorrect) {
		this.numCorrect = numCorrect;
		this.numIncorrect = numIncorrect;
	}

	public int getNumCorrect() {
		return numCorrect;
	}

	public int getNumIncorrect() {
		return numIncorrect;
	}

}
