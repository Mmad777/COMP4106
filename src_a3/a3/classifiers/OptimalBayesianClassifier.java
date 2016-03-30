package a3.classifiers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.Covariance;

import a3.model.Iris;

public class OptimalBayesianClassifier extends Classifier {
	
	public  Map<String, ClassParameters> calcParameters(List<Iris> animals) {
		
		// Initialize the map to store mean vector/covariance for each class
		Map<String, ClassParameters> dataParams = new HashMap<String, ClassParameters>();
		
		// Partition data into sets for each class
		Map<String, List<Iris>> classMap = getClassMap(animals);
		
		// Do pairwise classification on all the classes
		for (String className : classMap.keySet()) {
			
			List<Iris> clazzData = classMap.get(className);
			double[] mean = calculateSampleMean(clazzData);
			double[][] covariance = calcFullCovarianceMatrix(clazzData);
			
			if (!dataParams.containsKey(className)) {
				ClassParameters params = new ClassParameters(className, mean, covariance);					
				dataParams.put(className, params);
				params.log();
			}
			
		}
		
		return dataParams;
		
	}
	
	private double[][] calcFullCovarianceMatrix(List<Iris> irises) {
		
		double[][] data = new double[irises.size()][Iris.NUM_FEATURES];
		
		for (int i=0; i<data.length; i++) {
			for (int j=0; j<data[i].length; j++) {
				data[i][j] = irises.get(i).getFeatures()[j];
			}
		}
		
		Covariance cov = new Covariance(data);
		return cov.getCovarianceMatrix().getData();
		
		
	}

}
