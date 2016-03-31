package a3.application;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import a3.classifiers.ClassificationResults;
import a3.classifiers.IClassifier;
import a3.classifiers.LinearClassifier;
import a3.classifiers.NaiveBayesClassifier;
import a3.classifiers.OptimalBayesianClassifier;
import a3.data.CSVParser;
import a3.data.DataSet;
import a3.data.Partitioner;
import a3.data.Partitioner.PartitionedData;
import a3.model.DataModel;
import a3.trees.DecisionTreeClassifier;

public class Application {
	
	private static final boolean USE_K_FOLD = false;
	private static final int K_FOLD = 10;
	
    public static void main(String[] args) throws InterruptedException, IOException {

//    	DataSet dataSet = CSVParser.parseIrisDataset();
//    	DataSet dataSet = CSVParser.parseHeartDataset();
//    	DataSet dataSet = CSVParser.parseWineDataset();
    	
    	System.out.println("Dataset: " + dataSet.getName());
    	
//    	System.out.println("Optimal Bayesian Classifier\n");
//    	testClassifier(new OptimalBayesianClassifier(), dataSet);
    	
//    	System.out.println("Naive Bayesian Classifier\n");
//    	testClassifier(new NaiveBayesClassifier(), dataSet);
    	
    	System.out.println("Linear Classifier\n");
    	testClassifier(new LinearClassifier(), dataSet);
        
    }
    
    private static void testClassifier(IClassifier classifier, DataSet dataSet) {
    	
    	Map<String, List<DataModel>> dataClassMap = Partitioner.getClassMap(dataSet.getData());
    	
    	if (USE_K_FOLD)
    		kFoldClassification(classifier, dataClassMap);
    	else
    		leaveOneOutClassification(classifier, dataSet.getData());
    	
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
		System.out.println("Total/average correct: " + numCorrect + "/" + round((numCorrect / numIterations), 4));
		System.out.println("Total/average incorrect: " + numIncorrect + "/" + round((numIncorrect / numIterations), 4));
		
	}
	
	private static double round(double value, int places) {
		
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	    
	}
    
}