package a3.application;

import java.io.IOException;

import a3.classifiers.NaiveBayesClassifier;
import a3.data.CSVParser;

public class Application {
	
    public static void main(String[] args) throws InterruptedException, IOException {
    	
    	NaiveBayesClassifier nbClassifier = new NaiveBayesClassifier();
    	nbClassifier.train(CSVParser.parseZooDataset());
        
    }
}