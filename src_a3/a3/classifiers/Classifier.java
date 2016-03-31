package a3.classifiers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import a3.model.DataModel;

public abstract class Classifier implements IClassifier {

	protected final static int NUM_DEC_DIGITS = 12;
	
	protected Map<String, List<DataModel>> getClassMap(List<DataModel> dataMap) {
		
		Map<String, List<DataModel>> classMap = new HashMap<String, List<DataModel>>();
		for (DataModel a : dataMap) {
			
			String dataClass = a.getClassName();
			if (!classMap.containsKey(dataClass)) {
				classMap.put(dataClass, new ArrayList<DataModel>());
			}
			
			classMap.get(dataClass).add(a);
			
		}
		
		return classMap;		
		
	}
	
	protected double calcMahalanobisDist(DataModel data, ClassParameters params) {
		
		// Calculate the delta between dimensions and mean
		double[] delta = calcDelta(data.getDimensions(), params.getMeanVector());
		
		// Inverse the covariance matrix
		RealMatrix m = MatrixUtils.createRealMatrix(params.getCovarianceMatrix());
		double[][] mCovInv = new LUDecomposition(m).getSolver().getInverse().getData();
		
		// Transpose delta (turns into a 2D array)
		double[][] tmDelta = transposeDelta(delta);
		
		// Convert delta to 1x4 matrix
		double[][] mDelta = { delta };
		
		// Do some matrix mult to get the distance
		RealMatrix m1 = MatrixUtils.createRealMatrix(tmDelta);
		RealMatrix m2 = MatrixUtils.createRealMatrix(mCovInv);
		RealMatrix m3 = MatrixUtils.createRealMatrix(mDelta);
		RealMatrix distMatrix = (m3.multiply(m2)).multiply(m1);
		return distMatrix.getData()[0][0];
		
	}

	private double[][] transposeDelta(double[] delta) {
		
		double[][] result = new double[delta.length][1];
		for (int i=0; i<result.length; i++) {
			result[i][0] = delta[i];
		}
		
		return result;
		
	}
	
	protected double[][] calcFullCovarianceMatrix(List<DataModel> data) {

		// TODO - this is bad
		int dimensions = data.get(0).getNumDimensions();
		
		double[][] dataArr = new double[data.size()][dimensions];
		
		for (int i=0; i<dataArr.length; i++) {
			for (int j=0; j<dataArr[i].length; j++) {
				dataArr[i][j] = data.get(i).getDimensions()[j];
			}
		}
		
		Covariance cov = new Covariance(dataArr);
		return cov.getCovarianceMatrix().getData();
		
		
	}
	
	// TODO - Remove if not used
	protected double[][] calcFullCovarianceMatrix2(List<DataModel> data, double[] sampleMean) {

		// TODO - this is bad
		int dimensions = data.get(0).getNumDimensions();
		
		double[][] result = new double[dimensions][dimensions];
		for (int r=0; r<result.length; r++) {
			for (int c=0; c<result.length; c++) {
				
				double sum = 0;
				for (DataModel dataModel : data) {
					sum += ((dataModel.getDimensions()[r] - sampleMean[r]) * (dataModel.getDimensions()[c] - sampleMean[c]));
				}
				
				result[r][c] = sum / (data.size() -1);
				
			}
			
		}
		
		return result;

	}
	
	protected double[] calculateSampleMean(List<DataModel> data) {
		
		// TODO - this is bad
		int dimensions = data.get(0).getNumDimensions();
		
		double[] result = new double[dimensions];
		for (int i=0; i<result.length; i++) {
			
			double sum = 0;
			for (DataModel a : data) {
				sum += a.getDimensions()[i];
			}
			
			result[i] = sum / data.size();
			
		}
		
		return result;
		
	}
	
	protected double[] calcDelta(double[] x, double[] y) {
		
		double[] result = new double[x.length];
		for (int i=0; i<result.length; i++) {
			result[i] = x[i] - y[i];
		}
		return result;
	}
	
	public class ClassParameters {
		
		private String className;
		private double[] meanVector;
		private double[][] covarianceMatrix;
		
		public ClassParameters(String className, double[] meanVector, double[][] covarianceMatrix) {
			this.className = className;
			this.meanVector = meanVector;
			this.covarianceMatrix = covarianceMatrix;			
		}

		public double[] getMeanVector() {
			return meanVector;
		}

		public double[][] getCovarianceMatrix() {
			return covarianceMatrix;
		}
		
		public void log() {
			
			logMean();
			logCovariance();
			
		}
		
		private void logMean() {
			
			System.out.print("Mean of " + className + ": [");
			for (int i=0; i<meanVector.length; i++) {
				System.out.print(round(meanVector[i], 6) + " ");
			}
			System.out.println("]");
		
		}
		
		private void logCovariance() {
			
			System.out.println("Covariance of " + className + ": \n{");
			
			for (int r=0; r<covarianceMatrix.length; r++) {
				System.out.print("\t[");
				for (int c=0; c<covarianceMatrix[r].length; c++) {
					double val = covarianceMatrix[r][c];
					System.out.print(round(val, 6) + " ");
				}
				System.out.println("]");
				
			}
			
			System.out.println("}");
		
		}
		
		/* TODO - Move to clibrary */
		private double round(double value, int places) {
			
		    if (places < 0) throw new IllegalArgumentException();

		    BigDecimal bd = new BigDecimal(value);
		    bd = bd.setScale(places, RoundingMode.HALF_UP);
		    return bd.doubleValue();
		    
		}

	}

}
