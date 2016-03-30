package a3.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import a3.data.Partitioner.PartitionedData;
import a3.model.Iris;

public class NaiveBayesClassifier extends Classifier {
	
	public Map<String, List<Iris>> classify(PartitionedData partitionedData) {
		
		// Calculate parameters
    	Map<String, ClassParameters> classParamMap = calcParameters(partitionedData.getTrainingData());
		
		// Initialize the result map
		Map<String, List<Iris>> result = new HashMap<String, List<Iris>>();
		
		// Classify test data
		partitionedData.getTestData().forEach(iris -> {
			
			String bestClass = null;
			double minDist = Double.MAX_VALUE;
			
			classParamMap.keySet().forEach(c -> {
				
				ClassParameters params = classParamMap.get(c);
				
				// Calculate the delta between dimensions and mean
				double[] delta = calcDelta(iris.getFeatures(), params.getMeanVector());
				
				// Inverse the covariance matrix
				RealMatrix m = MatrixUtils.createRealMatrix(params.getCovarianceMatrix());
				RealMatrix mInv = new LUDecomposition(m).getSolver().getInverse();
				double[][] inv = mInv.getData();
				
				// Transpose delta (turns into a 2D array)
//				double[][] tDelta = transposeDelta(delta);
//				
//				// TODO - Calculate the Mahalanobis distance
//				double[][] dotDeltaInv = dot(tDelta, inv);
				
				// TODO - If d < minDist, set minDist and bestClass
				
			});
			
			if (!result.containsKey(bestClass)) {
				result.put(bestClass, new ArrayList<Iris>());
			}
			
			result.get(bestClass).add(iris);
			
		});
		
		return result;
		
	}
	
	private Map<String, ClassParameters> calcParameters(List<Iris> data) {
		
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
