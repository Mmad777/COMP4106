package a3.classifiers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.model.Iris;

public class NaiveBayesClassifier extends Classifier {
	
	public Map<String, ClassParameters> calcParameters(List<Iris> data) {
		
		// Initialize the map to store mean vector/covariance for each class
		Map<String, ClassParameters> statsMap = new HashMap<String, ClassParameters>();
		
		// Partition data into sets for each class
		Map<String, List<Iris>> classMap = getClassMap(data);
		
		// Calculate parameters for each class
		for (String className : classMap.keySet()) {
			
			List<Iris> clazzData = classMap.get(className);
			double[] mean = calculateSampleMean(clazzData);
			double[][] covariance = calcDiagonalCovarianceMatrix(clazzData, mean);
			
			if (!statsMap.containsKey(className)) {
				ClassParameters params = new ClassParameters(className, mean, covariance);					
				statsMap.put(className, params);
				params.log();
			}
			
		}
		
		return statsMap;
		
	}
	
	private double[][] calcDiagonalCovarianceMatrix(List<Iris> irises, double[] sampleMean) {
		
		double[][] result = new double[Iris.NUM_FEATURES][Iris.NUM_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			double sum = 0;
			for (Iris a : irises) {
				sum += Math.pow(a.getFeatures()[i] - sampleMean[i], 2);
			}
			
			result[i][i] = Math.pow(sum / irises.size(), 2);
			
		}
		
		return result;
		
	}

}
