package a3.application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import a3.classifiers.OptimalBayesianClassifier;
import a3.data.CSVParser;
import a3.data.Partitioner;
import a3.data.Partitioner.PartitionedData;
import a3.model.Iris;

public class Application {
	
    public static void main(String[] args) throws InterruptedException, IOException {
    	
    	testOptimalBayesianClassifier();
        
    }
    
    private static void testOptimalBayesianClassifier() throws IOException {
    	
    	List<Iris> irisData = CSVParser.parseIrisDataset();
    	
    	// Split it into classes
    	Map<String, List<Iris>> irisDataClassMap = Partitioner.getClassMap(irisData);
    	
    	// Partition into training/test sets
    	PartitionedData partitionedData = Partitioner.getPartitionedData(10, irisDataClassMap);
    	
    	// Classify test data
    	OptimalBayesianClassifier obClassifier = new OptimalBayesianClassifier();
    	obClassifier.classify(partitionedData);
    	
    	
    }
    
}