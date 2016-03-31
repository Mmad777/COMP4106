package a3.application;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import a3.classifiers.ClassificationResults;
import a3.classifiers.IClassifier;
import a3.classifiers.LinearClassifier;
import a3.classifiers.NaiveBayesClassifier;
import a3.classifiers.OptimalBayesianClassifier;
import a3.data.CSVParser;
import a3.data.Partitioner;
import a3.data.Partitioner.PartitionedData;
import a3.model.DataModel;

public class Application {
	
	private static final boolean USE_K_FOLD = false;
	private static final int K_FOLD = 10;
	
    public static void main(String[] args) throws InterruptedException, IOException {

//    	List<DataModel> data = CSVParser.parseHeartDataset();
//    	List<DataModel> data = CSVParser.parseWineDataset();
    	List<DataModel> data = CSVParser.parseIrisDataset();
    	
//    	System.out.println("Naive Bayesian Classifier");
//    	testClassifier(new NaiveBayesClassifier(), data);
    	
    	System.out.println("Optimal Bayesian Classifier");
    	testClassifier(new OptimalBayesianClassifier(), data);
    	
//    	System.out.println("Linear Classifier");
//    	testClassifier(new LinearClassifier(), data);
        
    }
    
    private static void testClassifier(IClassifier classifier, List<DataModel> data) {
    	
    	Map<String, List<DataModel>> dataClassMap = Partitioner.getClassMap(data);
    	
    	if (USE_K_FOLD)
    		kFoldClassification(classifier, dataClassMap);
    	else
    		leaveOneOutClassification(classifier, data);
    	
    }
    
    private static void leaveOneOutClassification(IClassifier classifier, List<DataModel> data) {

		int totalCorrect = 0, totalIncorrect = 0;
    	for (int i=0; i<data.size(); i++) {
    		
    		System.out.println("Leave-one-out iteration = " + i);

        	// Partition into training/test sets
        	PartitionedData partitionedData = Partitioner.getLeaveOneOutPartitionedData(i, data);
        	
        	// Classify test data
        	Map<String, List<DataModel>> classified = classifier.classify(partitionedData);

        	// Verify whether classifications were correct or incorrect
        	ClassificationResults results = verifyClassification(classified);
        	
        	// Sum the correct/incorrect
        	totalCorrect += results.getNumCorrect();
        	totalIncorrect += results.getNumIncorrect();
    		
    	}
    	
    	logResults(totalCorrect, totalIncorrect, data.size());
    	
    }
	
	private static void kFoldClassification(IClassifier classifier, Map<String, List<DataModel>> dataClassMap) {
    	
		int totalCorrect = 0, totalIncorrect = 0;
    	for (int i=0; i<K_FOLD; i++) {
    		
    		System.out.println("K-fold iteration = " + i);

        	// Partition into training/test sets
        	PartitionedData partitionedData = Partitioner.getKFoldPartitionedData(i, K_FOLD, dataClassMap);
        	
        	// Classify test data
        	Map<String, List<DataModel>> classified = classifier.classify(partitionedData);
        	
        	// Verify whether classifications were correct or incorrect
        	ClassificationResults results = verifyClassification(classified);
        	
        	// Sum the correct/incorrect
        	totalCorrect += results.getNumCorrect();
        	totalIncorrect += results.getNumIncorrect();
    		
    	}
    	
    	logResults(totalCorrect, totalIncorrect, K_FOLD);
		
	}
	
	private static ClassificationResults verifyClassification(Map<String, List<DataModel>> classified) {
    	
		int numCorrect = 0, numIncorrect = 0;
		
    	for (String className : classified.keySet()) {
    		
    		for (DataModel dataModel : classified.get(className)) {
    			
    			boolean correctClass = dataModel.getClassName().equals(className);
    			numCorrect += correctClass ? 1 : 0;
    			numIncorrect += correctClass ? 0 : 1;
    			
    		}
    		
    	}
    	
    	System.out.println("Correct classifications: " + numCorrect);
    	System.out.println("Incorrect classifications: " + numIncorrect);
    	return new ClassificationResults(numCorrect, numIncorrect);
		
	}
	
	private static void logResults(double numCorrect, double numIncorrect, int numIterations) {
		
		System.out.println("\n----- RESULTS -----");
		System.out.println("Total correct: " + numCorrect);
		System.out.println("Total incorrect: " + numIncorrect);
		System.out.println("Average correct: " + (numCorrect / numIterations));
		System.out.println("Average incorrect: " + (numIncorrect / numIterations));
		
	}
    
}