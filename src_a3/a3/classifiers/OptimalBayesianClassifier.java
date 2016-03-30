package a3.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import a3.data.Partitioner.PartitionedData;
import a3.model.DataModel;

public class OptimalBayesianClassifier extends Classifier {
	
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
				
				// Calculate the delta between dimensions and mean
				double[] delta = calcDelta(data.getDimensions(), params.getMeanVector());
				
				// Inverse the covariance matrix
				RealMatrix m = MatrixUtils.createRealMatrix(params.getCovarianceMatrix());
				double[][] mCovInv = new LUDecomposition(m).getSolver().getInverse().getData();
				
				// Transpose delta (turns into a 2D array)
				double[][] tmDelta = transposeDelta(delta);
				
				// Convert delta to 1x4 matrix
				double[][] mDelta = { delta };
				
				// TODO - Calculate the Mahalanobis distance
				RealMatrix m1 = MatrixUtils.createRealMatrix(tmDelta);
				RealMatrix m2 = MatrixUtils.createRealMatrix(mCovInv);
				RealMatrix m3 = MatrixUtils.createRealMatrix(mDelta);
				RealMatrix test = m1.multiply(m2);
				RealMatrix test2 = m3.multiply(test);
				
				// TODO - If d < minDist, set minDist and bestClass
//				double d = 0;
//				if (d < minDist) {
//					minDist = d;
//					bestClass = className;
//				}
				
			}
			
			if (!result.containsKey(bestClass)) {
				result.put(bestClass, new ArrayList<DataModel>());
			}
			
			result.get(bestClass).add(data);
			
		});
		
		return result;
		
	}
	
	private  Map<String, ClassParameters> calcParameters(List<DataModel> data) {
		
		// Initialize the map to store mean vector/covariance for each class
		Map<String, ClassParameters> dataParams = new HashMap<String, ClassParameters>();
		
		// Partition data into sets for each class
		Map<String, List<DataModel>> classMap = getClassMap(data);
		
		// Do pairwise classification on all the classes
		for (String className : classMap.keySet()) {
			
			List<DataModel> clazzData = classMap.get(className);
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

}
