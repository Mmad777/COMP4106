package a3.application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import a3.classifiers.NaiveBayesClassifier;
import a3.classifiers.OptimalBayesianClassifier;
import a3.data.CSVParser;
import a3.data.Partitioner;
import a3.data.Partitioner.PartitionedData;
import a3.model.DataModel;

public class Application {
	
    public static void main(String[] args) throws InterruptedException, IOException {

//    	System.out.println("\nNaive Bayesian Classifier");
//    	testNaiveBayesianClassifier();
    	
    	System.out.println("\nOptimal Bayesian Classifier");
    	testOptimalBayesianClassifier();
        
    }
    
    private static void testNaiveBayesianClassifier() throws IOException {
    	
    	List<DataModel> irisData = CSVParser.parseIrisDataset();
    	
    	// Split it into classes
    	Map<String, List<DataModel>> irisDataClassMap = Partitioner.getClassMap(irisData);
    	
    	// Partition into training/test sets
    	PartitionedData partitionedData = Partitioner.getPartitionedData(10, irisDataClassMap);
    	
    	// Classify test data
    	NaiveBayesClassifier nbClassifier = new NaiveBayesClassifier();
    	nbClassifier.classify(partitionedData);
		
	}

	private static void testOptimalBayesianClassifier() throws IOException {
    	
    	List<DataModel> irisData = CSVParser.parseIrisDataset();
    	
    	// Split it into classes
    	Map<String, List<DataModel>> irisDataClassMap = Partitioner.getClassMap(irisData);
    	
    	// Partition into training/test sets
    	PartitionedData partitionedData = Partitioner.getPartitionedData(10, irisDataClassMap);
    	
    	// Classify test data
    	OptimalBayesianClassifier obClassifier = new OptimalBayesianClassifier();
    	obClassifier.classify(partitionedData);
    	
    	
    }
    
}