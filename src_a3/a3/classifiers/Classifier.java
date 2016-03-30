package a3.classifiers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
	
	protected Double[] calculateSampleMean(List<Iris> irises) {
		
		Double[] result = new Double[Iris.NUM_FEATURES];
		for (int i=0; i<result.length; i++) {
			
			Double sum = new Double(0);
			for (Iris a : irises) {
				sum += a.getFeatures()[i];
			}
			
			result[i] = sum / irises.size();
			
		}
		
		return result;
		
	}
	
	protected void logMean(String className, Double[] arr) {
		
		System.out.print("Mean of " + className + ": [");
		Stream.of(arr).forEach(i -> {
			System.out.print(round(i, 6) + " ");
		});
		System.out.println("]");
	
	}
	
	protected void logCovariance(String className, double[][] matrix) {
		
		System.out.println("Covariance of " + className + ": \n{");
		
		for (int r=0; r<matrix.length; r++) {
			System.out.print("\t[");
			for (int c=0; c<matrix[r].length; c++) {
				double val = matrix[r][c];
				System.out.print(round(val, 6) + " ");
			}
			System.out.println("]");
			
		}
		
		System.out.println("}");
	
	}
	
	/* TODO - Move to clibrary */
	protected static double round(double value, int places) {
		
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	    
	}

}
