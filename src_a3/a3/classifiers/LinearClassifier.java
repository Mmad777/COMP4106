package a3.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.data.Partitioner.PartitionedData;
import a3.model.DataModel;

public class LinearClassifier extends Classifier {
	
	public Map<String, List<DataModel>> classify(PartitionedData partitionedData) {
		
		// Calculate parameters
    	Map<String, ClassParameters> paramMap = calcParameters(partitionedData.getTrainingData());
		
		// Initialize the result map
		Map<String, List<DataModel>> result = new HashMap<String, List<DataModel>>();
		
		// Classify test data
		partitionedData.getTestData().forEach(data -> {
			
			String bestClass = null;
			double minDist = Double.MAX_VALUE;
			
			for (String className : paramMap.keySet()) {
				
				ClassParameters params = paramMap.get(className);
				double distance = calcEuclidDistance(data, params);		
				if (distance < minDist) {
					minDist = distance;
					bestClass = className;
				}
				
			}
			
			if (!result.containsKey(bestClass)) {
				result.put(bestClass, new ArrayList<DataModel>());
			}
			
			result.get(bestClass).add(data);
			
		});
		
		return result;
		
	}
	
	private double calcEuclidDistance(DataModel data, ClassParameters params) {
		
		double sum = 0;
		for (int i=0; i<data.getNumDimensions(); i++) {
			sum += Math.pow((data.getDimensions()[i] - params.getMeanVector()[i]), 2);
		}
		
		return Math.sqrt(sum);
		
	}

	private  Map<String, ClassParameters> calcParameters(List<DataModel> data) {
		
		// Initialize the map to store mean vector/covariance for each class
		Map<String, ClassParameters> dataParams = new HashMap<String, ClassParameters>();
		
		// Partition data into sets for each class
		Map<String, List<DataModel>> classMap = getClassMap(data);
		
		// Use the same covariance matrix for all classes
		double[][] covariance = calcFullCovarianceMatrix(data);
		
		// Do pairwise classification on all the classes
		for (String className : classMap.keySet()) {
			
			List<DataModel> clazzData = classMap.get(className);
			double[] mean = calculateSampleMean(clazzData);
			
			if (!dataParams.containsKey(className)) {
				ClassParameters params = new ClassParameters(className, mean, covariance);					
				dataParams.put(className, params);
				params.log();
			}
			
		}
		
		return dataParams;
		
	}

}
