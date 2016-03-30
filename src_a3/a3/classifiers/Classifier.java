package a3.classifiers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a3.model.Iris;

public abstract class Classifier {

	protected final static int NUM_DEC_DIGITS = 12;
	
	protected Map<String, List<Iris>> getClassMap(List<Iris> irisMap) {
		
		Map<String, List<Iris>> classMap = new HashMap<String, List<Iris>>();
		for (Iris a : irisMap) {
			
			String dataClass = a.getDataClass();
			if (!classMap.containsKey(dataClass)) {
				classMap.put(dataClass, new ArrayList<Iris>());
			}
			
			classMap.get(dataClass).add(a);
			
		}
		
		return classMap;		
		
	}
	
	protected double[] calculateSampleMean(List<Iris> irises) {
		
		double[] result = new double[Iris.NUM_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			double sum = 0;
			for (Iris a : irises) {
				sum += a.getFeatures()[i];
			}
			
			result[i] = sum / irises.size();
			
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
