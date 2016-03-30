package a3.application;

import java.io.IOException;

import a3.classifiers.OptimalBayesianClassifier;
import a3.data.CSVParser;
import a3.data.Partitioner;
import a3.data.Partitioner.PartitionedData;

public class Application {
	
    public static void main(String[] args) throws InterruptedException, IOException {
    	
//    	NaiveBayesClassifier nbClassifier = new NaiveBayesClassifier();
//    	nbClassifier.train(CSVParser.parseIrisDataset());
    	
    	PartitionedData data = Partitioner.getPartitionedData(10, CSVParser.parseIrisDataset());
    	OptimalBayesianClassifier obClassifier = new OptimalBayesianClassifier();
    	obClassifier.train(data.getTrainingData());
        
    }
}