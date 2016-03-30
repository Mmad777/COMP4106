package a3.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import a3.data.Partitioner.PartitionedData;
import a3.model.Iris;

public class OptimalBayesianClassifier extends Classifier {
	
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
				double[][] tDelta = transposeDelta(delta);
				
				// TODO - Calculate the Mahalanobis distance
				double[][] dotDeltaInv = dot(tDelta, inv);
				
				// TODO - If d < minDist, set minDist and bestClass
				
			});
			
			if (!result.containsKey(bestClass)) {
				result.put(bestClass, new ArrayList<Iris>());
			}
			
			result.get(bestClass).add(iris);
			
		});
		
		return result;
		
	}
	
	private  Map<String, ClassParameters> calcParameters(List<Iris> data) {
		
		// Initialize the map to store mean vector/covariance for each class
		Map<String, ClassParameters> dataParams = new HashMap<String, ClassParameters>();
		
		// Partition data into sets for each class
		Map<String, List<Iris>> classMap = getClassMap(data);
		
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
	
	private double[][] dot(double[][] x, double[][] y) {

		double[][] result = new double[x.length][y.length];
		for (int r=0; r<x.length; r++) {
			for (int c=0; c<x.length; c++) {
				
				double val = 0;
				for (int i=0; i<x.length; i++) {
					val += x[r][i] * y[i][c];
				}
				
				result[r][c] = val;
				
			}
		}
		
		return result;
		
	}

	private double[][] transposeDelta(double[] delta) {
		
		double[][] mDelta = new double[delta.length][delta.length];
		mDelta[0] = delta;
		for (int row=1; row<mDelta.length; row++) {
			for (int col=0; col<mDelta[row].length; col++){
				mDelta[row][col] = 0;
			}
		}
		RealMatrix rmDelta = MatrixUtils.createRealMatrix(mDelta);
		return rmDelta.transpose().getData();
		
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
